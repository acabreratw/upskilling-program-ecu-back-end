package com.thoughtworks.lpe.be_template.domains.builders;

import com.thoughtworks.lpe.be_template.dtos.CourseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CourseBuilder {
    private CourseDto courseDto;

    public CourseBuilder() {
        this.courseDto = new CourseDto();
    }

    public CourseBuilder withName(String name){
        this.courseDto.setName(name);
        return this;
    }

    public CourseBuilder withDescription(String description){
        this.courseDto.setDescription(description);
        return this;
    }

    public CourseBuilder withPrice(BigDecimal price){
        this.courseDto.setPrice(price);
        return this;
    }

    public CourseBuilder withImageUrl(String imageUrl){
        this.courseDto.setImageUrl(imageUrl);
        return this;
    }

    public CourseBuilder withFreeStartDate(LocalDateTime freeStartDate){
        this.courseDto.setFreeStartDate(freeStartDate);
        return this;
    }

    public CourseBuilder withFreeEndDate(LocalDateTime freeEndDate){
        this.courseDto.setFreeEndDate(freeEndDate);
        return this;
    }

    public CourseBuilder withId(int id) {
        this.courseDto.setId(id);
        return this;
    }

    public CourseDto build(){
        return this.courseDto;
    }
}
