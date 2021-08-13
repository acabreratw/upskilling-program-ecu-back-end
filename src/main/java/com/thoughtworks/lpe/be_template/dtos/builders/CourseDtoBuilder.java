package com.thoughtworks.lpe.be_template.dtos.builders;

import com.thoughtworks.lpe.be_template.domains.Course;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CourseDtoBuilder {
    private Course course;

    public CourseDtoBuilder() {
        this.course = new Course();
    }

    public CourseDtoBuilder withName(String name){
        this.course.setName(name);
        return this;
    }

    public CourseDtoBuilder withDescription(String description){
        this.course.setDescription(description);
        return this;
    }

    public CourseDtoBuilder withPrice(BigDecimal price){
        this.course.setPrice(price);
        return this;
    }

    public CourseDtoBuilder withImageUrl(String imageUrl){
        this.course.setImageUrl(imageUrl);
        return this;
    }

    public CourseDtoBuilder withFreeStartDate(LocalDateTime freeStartDate){
        this.course.setFreeStartDate(freeStartDate);
        return this;
    }

    public CourseDtoBuilder withFreeEndDate(LocalDateTime freeEndDate){
        this.course.setFreeEndDate(freeEndDate);
        return this;
    }

    public CourseDtoBuilder withId(int id) {
        this.course.setId(id);
        return this;
    }

    public Course build(){
        return this.course;
    }
}

