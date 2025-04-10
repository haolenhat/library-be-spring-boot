package com.example.librabry_spring_boot.service;


import com.example.librabry_spring_boot.dto.LoanRecordRequestDTO;
import com.example.librabry_spring_boot.dto.LoanRecordResponseDTO;
import com.example.librabry_spring_boot.model.*;
import com.example.librabry_spring_boot.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanRecordService {

    private final LoanRecordRepository loanRecordRepository;

    private final UserRepository userRepository;

    private final BorrowerRepository borrowerRepository;

    private final BookRepository bookRepository;

    private final LoanCardRepository loanCardRepository;

    @PostConstruct
    public void init() {
        updateOverdueStatuses(); // chạy ngay khi app vừa start
    }

    public LoanRecordService(LoanRecordRepository loanRecordRepository, UserRepository userRepository, BorrowerRepository borrowerRepository, BookRepository bookRepository, LoanCardRepository loanCardRepository) {
        this.loanRecordRepository = loanRecordRepository;
        this.userRepository = userRepository;
        this.borrowerRepository = borrowerRepository;
        this.bookRepository = bookRepository;
        this.loanCardRepository = loanCardRepository;
    }

    // hiển thị danh sách phiếu mượn
    public List<LoanRecordResponseDTO> getAllLoanRecords() {
        List<LoanRecord> loanRecords = loanRecordRepository.findAll();

        return loanRecords.stream().map(loan -> {
            LoanRecordResponseDTO dto = new LoanRecordResponseDTO();

            dto.setLoanId(loan.getLoanId());
            dto.setBorrowerCode(loan.getBorrower().getBorrowerCode());
            dto.setBorrowerName(loan.getBorrower().getFullName());

            if (loan.getLibrarian() != null) {
                dto.setLibrarianId(loan.getLibrarian().getUserId());
                dto.setLibrarianName(loan.getLibrarian().getFullName());
            }

            dto.setBorrowDate(loan.getBorrowDate());
            dto.setReturnDate(loan.getReturnDate());
            dto.setStatus(loan.getStatus());
            dto.setLostOrDamagedFee(loan.getLostOrDamagedFee());

            List<LoanRecordResponseDTO.LoanCardInfo> loanCardInfos = loan.getLoanCards().stream().map(card -> {
                LoanRecordResponseDTO.LoanCardInfo cardInfo = new LoanRecordResponseDTO.LoanCardInfo();
                cardInfo.setLoancardId(card.getLoancardId());
                cardInfo.setBookId(card.getBook().getBookId());
                cardInfo.setBookTitle(card.getBook().getTitle());
                cardInfo.setQuantity(card.getQuantity());
                return cardInfo;
            }).collect(Collectors.toList());

            dto.setLoanCards(loanCardInfos);

            return dto;
        }).collect(Collectors.toList());
    }

    // chuyển đổi sang DTO
    private LoanRecordResponseDTO convertToDTO(LoanRecord record) {
        LoanRecordResponseDTO dto = new LoanRecordResponseDTO();

        dto.setLoanId(record.getLoanId());
        dto.setBorrowerCode(record.getBorrower().getBorrowerCode());
        dto.setBorrowerName(record.getBorrower().getFullName());

        if (record.getLibrarian() != null) {
            dto.setLibrarianId(record.getLibrarian().getUserId());
            dto.setLibrarianName(record.getLibrarian().getFullName());
        }

        dto.setBorrowDate(record.getBorrowDate());
        dto.setReturnDate(record.getReturnDate());
        dto.setStatus(record.getStatus());
        dto.setLostOrDamagedFee(record.getLostOrDamagedFee());

        if (record.getLoanCards() != null) {
            List<LoanRecordResponseDTO.LoanCardInfo> loanCardInfos = record.getLoanCards().stream().map(card -> {
                LoanRecordResponseDTO.LoanCardInfo cardInfo = new LoanRecordResponseDTO.LoanCardInfo();
                cardInfo.setLoancardId(card.getLoancardId());
                cardInfo.setBookId(card.getBook().getBookId());
                cardInfo.setBookTitle(card.getBook().getTitle());
                cardInfo.setQuantity(card.getQuantity());
                return cardInfo;
            }).collect(Collectors.toList());

            dto.setLoanCards(loanCardInfos);
        } else {
            dto.setLoanCards(List.of()); // trả về list rỗng nếu chưa có loanCards
        }

        return dto;
    }

    // tạo phiếu mượn
    public LoanRecordResponseDTO createLoanRecord(LoanRecordRequestDTO requestDTO) {
        // Tìm hoặc tạo Borrower
        Borrower borrower = borrowerRepository.findByBorrowerCode(requestDTO.getBorrowerCode())
                .map(existing -> {
                    existing.setFullName(requestDTO.getBorrowerName()); // update tên nếu đã có
                    return borrowerRepository.save(existing);
                })
                .orElseGet(() -> {
                    Borrower newBorrower = new Borrower();
                    newBorrower.setBorrowerCode(requestDTO.getBorrowerCode());
                    newBorrower.setFullName(requestDTO.getBorrowerName());
                    return borrowerRepository.save(newBorrower);
                });

        User librarian = userRepository.findById(
                requestDTO.getLibrarianId().longValue() // Ép Integer sang Long
        ).orElseThrow(() -> new RuntimeException("Librarian không tồn tại"));

        LoanRecord record = new LoanRecord();
        record.setBorrower(borrower);
        record.setLibrarian(librarian);
        // Thay đổi ở đây để sử dụng thời gian hiện tại
        record.setBorrowDate(LocalDateTime.now()); // Thiết lập ngày mượn là thời gian hiện tại
        record.setStatus(requestDTO.getStatus());
        record.setLostOrDamagedFee(requestDTO.getLostOrDamagedFee());

        // Nếu returnDate rỗng => +5 ngày
        if (requestDTO.getReturnDate() == null) {
            record.setReturnDate(record.getBorrowDate().plusDays(5)); // Thiết lập ngày trả là 5 ngày sau ngày mượn
        } else {
            record.setReturnDate(requestDTO.getReturnDate());
        }

        loanRecordRepository.save(record);

        // Xử lý LoanCard
        for (LoanRecordRequestDTO.LoanCardInput cardInput : requestDTO.getLoanCards()) {
            Book book = bookRepository.findById(cardInput.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book không tồn tại"));

            int soLuongMuon = cardInput.getQuantity();
            int soLuongConLai = book.getAvailableCopies() != null ? book.getAvailableCopies() : 0;

            if (soLuongMuon > soLuongConLai) {
                throw new RuntimeException("Đã vượt quá số lượng sách cho phép. Số lượng sách còn lại trong thư viện là: " + soLuongConLai);
            }

            LoanCard card = new LoanCard();
            card.setLoanRecord(record);
            card.setBook(book);
            card.setQuantity(soLuongMuon);

            loanCardRepository.save(card);

            // Cập nhật số lượng sách còn lại
            book.setAvailableCopies(soLuongConLai - soLuongMuon);
            bookRepository.save(book);
        }

        return convertToDTO(record); // Trả về DTO đã tạo
    }

    // tự động cập nhật
    @Scheduled(cron = "0 */5 * * * ?") // tự động cập nhật mỗi 5 phút
    @Transactional
    public void updateOverdueStatuses() {
        List<LoanRecord> records = loanRecordRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (LoanRecord record : records) {
            // Nếu trạng thái là "Borrowed" và quá hạn thì chuyển sang "OverDue"
            if ("Borrowed".equals(record.getStatus()) && record.getReturnDate() != null) {
                if (record.getReturnDate().isBefore(now)) {
                    record.setStatus("OverDue");
                }
            }

            // Nếu trạng thái là "Lost", trừ số lượng sách
            if ("Lost".equalsIgnoreCase(record.getStatus())) {
                for (LoanCard card : record.getLoanCards()) {
                    Book book = card.getBook();
                    int quantityLost = card.getQuantity(); // số lượng sách đã mất
                    int availableCopies = book.getAvailableCopies() != null ? book.getAvailableCopies() : 0;

                    // Trừ số lượng sách còn lại
                    book.setAvailableCopies(Math.max(0, availableCopies - quantityLost));
                    bookRepository.save(book); // Cập nhật lại số lượng sách
                }
                // Không thay đổi trạng thái sang trạng thái khác
            }
        }

        loanRecordRepository.saveAll(records);
    }

    // api sửa thông tin phiếu mượn
    public LoanRecordResponseDTO updateLoanRecord(Integer loanId, LoanRecordRequestDTO requestDTO) {
        // Tìm phiếu mượn theo ID
        LoanRecord record = loanRecordRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan record not found"));

        // Cập nhật ngày trả
        if (requestDTO.getReturnDate() != null) {
            record.setReturnDate(requestDTO.getReturnDate());
        }

        // Cập nhật sách mượn và số lượng mượn
        if (requestDTO.getLoanCards() != null) {
            for (LoanRecordRequestDTO.LoanCardInput cardInput : requestDTO.getLoanCards()) {
                // Tìm loan card theo ID
                LoanCard loanCard = loanCardRepository.findById(cardInput.getBookId()) // Sử dụng bookId thay vì loancardId
                        .orElseThrow(() -> new RuntimeException("Loan card not found"));

                // Cập nhật số lượng sách mượn
                if (cardInput.getQuantity() != 0) {
                    loanCard.setQuantity(cardInput.getQuantity());
                }

                loanCardRepository.save(loanCard); // Lưu thay đổi loanCard
            }
        }

        // Cập nhật trạng thái nếu có
        if (requestDTO.getStatus() != null) {
            record.setStatus(requestDTO.getStatus());
        }

        loanRecordRepository.save(record); // Lưu thay đổi của loan record
        return convertToDTO(record); // Trả về DTO đã cập nhật
    }


    public void deleteLoanRecord(Integer loanId) {
        // Tìm phiếu mượn theo ID
        LoanRecord record = loanRecordRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan record not found"));

        // Lấy tất cả các loan card liên quan
        List<LoanCard> loanCards = loanCardRepository.findByLoanRecord(record);

        // Cập nhật số lượng sách trong bảng books
        for (LoanCard loanCard : loanCards) {
            Book book = loanCard.getBook();
            int quantityReturned = loanCard.getQuantity(); // Số lượng sách đã mượn
            int availableCopies = book.getAvailableCopies() != null ? book.getAvailableCopies() : 0;

            // Tăng số lượng sách còn lại
            book.setAvailableCopies(availableCopies + quantityReturned);
            bookRepository.save(book); // Cập nhật lại số lượng sách
        }

        // Xóa tất cả các loan card liên quan
        loanCardRepository.deleteAll(loanCards); // Xóa các loan card liên quan

        // Xóa phiếu mượn
        loanRecordRepository.delete(record); // Xóa phiếu mượn
    }

}
