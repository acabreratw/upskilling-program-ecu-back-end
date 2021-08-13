package com.thoughtworks.lpe.be_template.dtos.builders;

import com.thoughtworks.lpe.be_template.dtos.CourseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CourseDtoBuilder {
    private CourseDto courseDto;

    public CourseDtoBuilder() {
        this.courseDto = new CourseDto();
    }

    public CourseDtoBuilder withName(String name){
        this.courseDto.setName(name);
        return this;
    }

    public CourseDtoBuilder withDescription(String description){
        this.courseDto.setDescription(description);
        return this;
    }

    public CourseDtoBuilder withPrice(BigDecimal price){
        this.courseDto.setPrice(price);
        return this;
    }

    public CourseDtoBuilder withImageUrl(String imageUrl){
        this.courseDto.setImageUrl(imageUrl);
        return this;
    }

    public CourseDtoBuilder withFreeStartDate(LocalDateTime freeStartDate){
        this.courseDto.setFreeStartDate(freeStartDate);
        return this;
    }

    public CourseDtoBuilder withFreeEndDate(LocalDateTime freeEndDate){
        this.courseDto.setFreeEndDate(freeEndDate);
        return this;
    }

    public CourseDtoBuilder withId(int id) {
        this.courseDto.setId(id);
        return this;
    }

    public CourseDto build(){
        return this.courseDto;
    }
}
