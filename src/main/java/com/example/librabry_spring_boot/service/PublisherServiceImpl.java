package com.example.librabry_spring_boot.service;

import com.example.librabry_spring_boot.model.Publisher;
import com.example.librabry_spring_boot.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService{
    @Autowired
    private PublisherRepository publisherRepository;

    // Lấy danh sách nhà xuất bản
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    // Thêm nhà xuất bản
    public Publisher addPublisher(Publisher publisher) {
        if (publisherRepository.findByPublisherNameIgnoreCase(publisher.getPublisherName().trim()).isPresent()) {
            throw new RuntimeException("Tên nhà xuất bản đã tồn tại!");
        }
        return publisherRepository.save(publisher);
    }

    // Sửa nhà xuất bản
    public Publisher updatePublisher(Integer id, Publisher updatedPublisher) {
        Publisher existing = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà xuất bản với ID: " + id));

        var publisherWithSameName = publisherRepository.findByPublisherNameIgnoreCase(updatedPublisher.getPublisherName().trim());
        if (publisherWithSameName.isPresent() && !publisherWithSameName.get().getPublisherId().equals(id)) {
            throw new RuntimeException("Tên nhà xuất bản đã tồn tại!");
        }

        existing.setPublisherName(updatedPublisher.getPublisherName().trim());
        return publisherRepository.save(existing);
    }

    // Xoá nhà xuất bản (nếu không có sách)
    public void deletePublisher(Integer id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà xuất bản với ID: " + id));

        if (publisher.getBooks() != null && !publisher.getBooks().isEmpty()) {
            throw new RuntimeException("Không thể xoá nhà xuất bản vì đang có sách!");
        }

        publisherRepository.deleteById(id);
    }
}
