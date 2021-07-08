package com.thoughtworks.lpe.be_template.dtos;

public enum CourseStatus {
    PRO("IN_PROGRESS"), APR("PASSED"), DES("FAILED"), VEN("EXPIRED");

    private final String description;

    CourseStatus(String description) {
        this.description = description;
    }

}
