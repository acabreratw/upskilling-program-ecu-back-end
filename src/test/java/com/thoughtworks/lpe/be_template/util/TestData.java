package com.thoughtworks.lpe.be_template.util;

import com.thoughtworks.lpe.be_template.dtos.CourseDetailDto;
import com.thoughtworks.lpe.be_template.dtos.ResourceDto;
import com.thoughtworks.lpe.be_template.dtos.TrainerDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

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
                .resources(Collections.unmodifiableSet(new HashSet<>(Arrays.asList(getResourceDTO(), getResourceDTO()))))
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
        final String FAKE_TRAINER_ID = "6";
        final String FAKE_TRAINER_DESCRIPTION = "This is a fake trainer description";
        final String FAKE_TRAINER_NAME = "Fake Trainer Name";

        return TrainerDto.builder()
                .id(FAKE_TRAINER_ID)
                .description(FAKE_TRAINER_DESCRIPTION)
                .name(FAKE_TRAINER_NAME)
                .image(FAKE_IMAGE_URL)
                .build();
    }
}
