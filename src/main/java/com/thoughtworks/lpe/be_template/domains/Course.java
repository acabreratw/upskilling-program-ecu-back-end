package com.thoughtworks.lpe.be_template.domains;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "course")
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", insertable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "free_start_date")
    private LocalDateTime freeStartDate;

    @Column(name = "free_end_date")
    private LocalDateTime freeEndDate;

    public Course() {
    }

    public Course(int id, String name, String description, BigDecimal price, String imageUrl, LocalDateTime freeStartDate, LocalDateTime freeEndDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.freeStartDate = freeStartDate;
        this.freeEndDate = freeEndDate;
    }

    public Course(String name, String description, BigDecimal price, String imageUrl, LocalDateTime freeStartDate, LocalDateTime freeEndDate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.freeStartDate = freeStartDate;
        this.freeEndDate = freeEndDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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