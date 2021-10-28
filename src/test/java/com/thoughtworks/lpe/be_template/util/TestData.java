package com.thoughtworks.lpe.be_template.util;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.Trainer;
import com.thoughtworks.lpe.be_template.domains.User;
import com.thoughtworks.lpe.be_template.domains.enums.UserType;
import com.thoughtworks.lpe.be_template.dtos.CourseDetailDto;
import com.thoughtworks.lpe.be_template.dtos.ResourceDto;
import com.thoughtworks.lpe.be_template.dtos.TrainerDto;
import com.thoughtworks.lpe.be_template.dtos.UserDto;

import java.time.LocalDateTime;
import java.util.Collections;

public class TestData {
    private final String FAKE_IMAGE_URL = "this_is_some_image_url_.png";

    public CourseDetailDto getCourseDetailDTO() {
        final int FAKE_COURSE_ID = 1;
        final String FAKE_COURSE_DESCRIPTION = "this is a course description";
        final String FAKE_COURSE_STATUS = "OPEN";
        final String FAKE_COURSE_TITLE = "This Is a Course Title";

        return CourseDetailDto.builder()
                .id(FAKE_COURSE_ID)
                .description(FAKE_COURSE_DESCRIPTION)
                .enrolled(false)
                .image(FAKE_IMAGE_URL)
                .resources(Collections.singleton(getResourceDTO()))
                .status(FAKE_COURSE_STATUS)
                .title(FAKE_COURSE_TITLE)
                .trainer(this.getTrainerDTO())
                .build();
    }

    public ResourceDto getResourceDTO() {
        final long FAKE_RESOURCE_ID = 2;
        final String FAKE_CONTENT = "This is a fake content";
        final String FAKE_RESOURCE_TITLE = "This Is A Fake Resource Title";

        return ResourceDto.builder()
                .id(FAKE_RESOURCE_ID)
                .content(FAKE_CONTENT)
                .title(FAKE_RESOURCE_TITLE)
                .image(FAKE_IMAGE_URL)
                .build();
    }

    public TrainerDto getTrainerDTO() {
        final String FAKE_TRAINER_ID = "1";
        final String FAKE_TRAINER_DESCRIPTION = "Best educator in the world. Won Nobel prize of Education in 2021";
        final String FAKE_TRAINER_NAME = "John Doe";
        final String FAKE_TRAINER_TITLE = "Best of the Best";
        final String FAKE_TRAINER_IMAGE = "https://media.istockphoto.com/photos/portrait-of-smiling-professor-in-the-amphitheater-picture-id1128666909?k=6&m=1128666909&s=612x612&w=0&h=gwBz0Hi_DIhpcwrX64agp-iYbGGehPpRfuw6KnsRU8s=";

        return TrainerDto.builder()
                .id(FAKE_TRAINER_ID)
                .description(FAKE_TRAINER_DESCRIPTION)
                .name(FAKE_TRAINER_NAME)
                .image(FAKE_TRAINER_IMAGE)
                .title(FAKE_TRAINER_TITLE)
                .build();
    }

    public Course getCourseWithFakeData() {
        final int FAKE_COURSE_ID = 1;
        final String FAKE_COURSE_DESCRIPTION = "this is a course description";
        final String FAKE_COURSE_NAME = "This Is a Course Title";

        return Course.builder()
                .id(FAKE_COURSE_ID)
                .description(FAKE_COURSE_DESCRIPTION)
                .freeStartDate(LocalDateTime.now())
                .freeEndDate(LocalDateTime.now())
                .imageUrl(FAKE_IMAGE_URL)
                .name(FAKE_COURSE_NAME)
                .trainer(this.getTrainer())
                .build();
    }

    private Trainer getTrainer() {
        final int FAKE_TRAINER_ID = 1;
        final String FAKE_TRAINER_DESCRIPTION = "Best educator in the world. Won Nobel prize of Education in 2021";
        final String FAKE_TRAINER_NAME = "John Doe";
        final String FAKE_TRAINER_TITLE = "Best of the Best";
        final String FAKE_TRAINER_IMAGE = "https://media.istockphoto.com/photos/portrait-of-smiling-professor-in-the-amphitheater-picture-id1128666909?k=6&m=1128666909&s=612x612&w=0&h=gwBz0Hi_DIhpcwrX64agp-iYbGGehPpRfuw6KnsRU8s=";

        return Trainer.builder()
                .id(FAKE_TRAINER_ID)
                .description(FAKE_TRAINER_DESCRIPTION)
                .name(FAKE_TRAINER_NAME)
                .image(FAKE_TRAINER_IMAGE)
                .degree(FAKE_TRAINER_TITLE)
                .build();
    }

    public User getUser(UserType userType) {
        return User.builder()
                .id("1231424231")
                .email("somemail@somedomain.com")
                .type(userType)
                .name("Some Name")
                .build();
    }

    public UserDto getUserDto(UserType userType) {
        return UserDto.builder()
                .id("1231424231")
                .email("somemail@somedomain.com")
                .type(userType)
                .name("Some Name")
                .build();
    }
}
