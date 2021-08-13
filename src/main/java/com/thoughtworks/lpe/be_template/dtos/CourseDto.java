package com.thoughtworks.lpe.be_template.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CourseDto {
    private int id;

    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private LocalDateTime freeStartDate;
    private LocalDateTime freeEndDate;
    public CourseDto() {}

    public CourseDto(String name, String description, BigDecimal price, String imageUrl, LocalDateTime freeStartDate, LocalDateTime freeEndDate, int id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.freeStartDate = freeStartDate;
        this.freeEndDate = freeEndDate;
        this.id = id;
    }

    public int getId() {return id; }

    public void setId(int id) { this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getFreeStartDate() {
        return freeStartDate;
    }

    public void setFreeStartDate(LocalDateTime freeStartDate) {
        this.freeStartDate = freeStartDate;
    }

    public LocalDateTime getFreeEndDate() {
        return freeEndDate;
    }

    public void setFreeEndDate(LocalDateTime freeEndDate) {
        this.freeEndDate = freeEndDate;
    }
}
