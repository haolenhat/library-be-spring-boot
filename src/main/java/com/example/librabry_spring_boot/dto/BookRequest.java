package com.example.librabry_spring_boot.dto;


import java.util.List;


public class BookRequest {
    private String title;
    private String linkImg;
    private Integer availableCopies;
    private String description;
    private String publisherName;
    private String categoryName;
    private List<String> authorNames;

    public BookRequest() {
    }

    public BookRequest(String title, String linkImg, Integer availableCopies, String description, String publisherName, String categoryName, List<String> authorNames) {
        this.title = title;
        this.linkImg = linkImg;
        this.availableCopies = availableCopies;
        this.description = description;
        this.publisherName = publisherName;
        this.categoryName = categoryName;
        this.authorNames = authorNames;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(List<String> authorNames) {
        this.authorNames = authorNames;
    }
}
