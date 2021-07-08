package com.thoughtworks.lpe.be_template.controllers.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CourseResource {

    @JsonProperty
    private int id;

    @JsonProperty
    private String name;
    @JsonProperty
    private String description;
    @JsonProperty
    private BigDecimal price;
    @JsonProperty
    private String imageUrl;
    @JsonProperty
    private String freeStartDate;
    @JsonProperty
    private String freeEndDate;
    public CourseResource() {}

    public CourseResource(String name, String description, BigDecimal price, String imageUrl, String freeStartDate, String freeEndDate, int id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.freeStartDate = freeStartDate;
        this.freeEndDate = freeEndDate;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        public String getFreeStartDate() {
        return freeStartDate;
    }

        public void setFreeStartDate(String freeStartDate) {
        this.freeStartDate = freeStartDate;
    }

        public String getFreeEndDate() {
        return freeEndDate;
    }

        public void setFreeEndDate(String freeEndDate) {
        this.freeEndDate = freeEndDate;
    }
    }

