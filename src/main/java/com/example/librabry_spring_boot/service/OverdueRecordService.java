package com.example.librabry_spring_boot.service;

import com.example.librabry_spring_boot.dto.FineInfoDTO;
import com.example.librabry_spring_boot.model.Borrower;
import com.example.librabry_spring_boot.model.LoanRecord;
import com.example.librabry_spring_boot.model.OverdueRecord;
import com.example.librabry_spring_boot.repository.LoanRecordRepository;
import com.example.librabry_spring_boot.repository.OverdueRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OverdueRecordService {

    private final LoanRecordRepository loanRecordRepository;
    private final OverdueRecordRepository overdueRecordRepository;

    @Autowired
    public OverdueRecordService(LoanRecordRepository loanRecordRepository,
                                OverdueRecordRepository overdueRecordRepository) {
        this.loanRecordRepository = loanRecordRepository;
        this.overdueRecordRepository = overdueRecordRepository;
    }

    // tự động kiểm tra quá hạn
    @Scheduled(cron = "0 */5 * * * ?")
    public void updateOverdueRecords() {
        List<LoanRecord> eligibleLoans = loanRecordRepository
                .findByStatusIn(List.of("Borrowed", "Returned", "OverDue"));

        for (LoanRecord loan : eligibleLoans) {
            if (overdueRecordRepository.existsByLoanRecord(loan)) {
                continue;
            }

            LocalDate dueDate = loan.getReturnDate().toLocalDate();
            LocalDate actualReturnDate;

            if (loan.getStatus().equals("Borrowed") || loan.getStatus().equals("OverDue")) {
                actualReturnDate = LocalDate.now();
            } else {
                actualReturnDate = loan.getReturnDate().toLocalDate();// hoặc loan.getActualReturnDate()
            }

            if (!actualReturnDate.isAfter(dueDate)) {
                continue;
            }

            OverdueRecord overdue = new OverdueRecord();
            overdue.setLoanRecord(loan);
            overdue.setDueDate(dueDate);
            overdue.setReturnDate(actualReturnDate.atStartOfDay());

            overdueRecordRepository.save(overdue);
        }
    }

    // danh sách người quá hạn đóng phạt
    public List<FineInfoDTO> getAllFines() {
        List<OverdueRecord> overdueRecords = overdueRecordRepository.findAll();
        List<FineInfoDTO> result = new ArrayList<>();

        for (OverdueRecord overdue : overdueRecords) {
            LoanRecord loan = overdue.getLoanRecord();
            Borrower borrower = loan.getBorrower();

            String bookTitle = loan.getLoanCards().stream()
                    .map(card -> card.getBook().getTitle())
                    .collect(Collectors.joining(", ")); // nếu có nhiều sách

            FineInfoDTO dto = new FineInfoDTO();
            dto.setBorrowerCode(borrower.getBorrowerCode());
            dto.setBorrowerName(borrower.getFullName());
            dto.setBookTitle(bookTitle);
            dto.setFineAmount(overdue.getFineAmount());
            dto.setOverdueDays(overdue.getOverdueDays());

            result.add(dto);
        }

        return result;
    }
}
