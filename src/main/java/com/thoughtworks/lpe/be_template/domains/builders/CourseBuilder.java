package com.thoughtworks.lpe.be_template.domains.builders;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CourseBuilder {
    private Course course;

    public CourseBuilder() {
        this.course = new Course();
    }

    public CourseBuilder withName(String name){
        this.course.setName(name);
        return this;
    }

    public CourseBuilder withDescription(String description){
        this.course.setDescription(description);
        return this;
    }

    public CourseBuilder withPrice(BigDecimal price){
        this.course.setPrice(price);
        return this;
    }

    public CourseBuilder withImageUrl(String imageUrl){
        this.course.setImageUrl(imageUrl);
        return this;
    }

    public CourseBuilder withFreeStartDate(LocalDateTime freeStartDate){
        this.course.setFreeStartDate(freeStartDate);
        return this;
    }

    public CourseBuilder withFreeEndDate(LocalDateTime freeEndDate){
        this.course.setFreeEndDate(freeEndDate);
        return this;
    }

    public CourseBuilder withId(int id) {
        this.course.setId(id);
        return this;
    }

    public Course build(){
        return this.course;
    }
}
