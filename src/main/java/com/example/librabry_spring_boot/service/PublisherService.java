package com.example.librabry_spring_boot.service;

import com.example.librabry_spring_boot.model.Publisher;

import java.util.List;

public interface PublisherService {
    public void deletePublisher(Integer id);
    public Publisher updatePublisher(Integer id, Publisher updatedPublisher);
    public Publisher addPublisher(Publisher publisher);
    public List<Publisher> getAllPublishers();
}
