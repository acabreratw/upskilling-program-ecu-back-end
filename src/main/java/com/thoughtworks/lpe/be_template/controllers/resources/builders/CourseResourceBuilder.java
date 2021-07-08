package com.thoughtworks.lpe.be_template.controllers.resources.builders;

import com.thoughtworks.lpe.be_template.controllers.resources.CourseResource;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CourseResourceBuilder {
    private CourseResource course;

    public CourseResourceBuilder() {
        this.course = new CourseResource();
    }

    public CourseResourceBuilder withName(String name){
        this.course.setName(name);
        return this;
    }

    public CourseResourceBuilder withDescription(String description){
        this.course.setDescription(description);
        return this;
    }

    public CourseResourceBuilder withPrice(BigDecimal price){
        this.course.setPrice(price);
        return this;
    }

    public CourseResourceBuilder withImageUrl(String imageUrl){
        this.course.setImageUrl(imageUrl);
        return this;
    }

    public CourseResourceBuilder withFreeStartDate(String freeStartDate){
        this.course.setFreeStartDate(freeStartDate);
        return this;
    }

    public CourseResourceBuilder withFreeEndDate(String freeEndDate){
        this.course.setFreeEndDate(freeEndDate);
        return this;
    }

    public CourseResourceBuilder withId(int id) {
        this.course.setId(id);
        return this;
    }

    public CourseResource build(){
        return this.course;
    }
}