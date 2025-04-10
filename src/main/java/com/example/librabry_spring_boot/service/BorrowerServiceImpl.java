package com.example.librabry_spring_boot.service;

import com.example.librabry_spring_boot.model.Borrower;
import com.example.librabry_spring_boot.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowerServiceImpl implements BorrowerService{
    @Autowired
    private BorrowerRepository borrowerRepository;

    // thêm người mượn
    public Borrower addBorrower(Borrower borrower) {
        Optional<Borrower> existing = borrowerRepository.findById(borrower.getBorrowerCode());
        if (existing.isPresent()) {
            throw new RuntimeException("Mã người mượn đã tồn tại");
        }
        return borrowerRepository.save(borrower);
    }
    //danh sách người mượn
    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }

    // xoá người mượn
    public void deleteBorrowerByCode(String code) {
        borrowerRepository.deleteById(code);
    }
    // sửa thông tin người mượn
    public Borrower updateBorrower(String code, Borrower updated) {
        Borrower borrower = borrowerRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người mượn"));
        borrower.setFullName(updated.getFullName());
        return borrowerRepository.save(borrower);
    }

}
