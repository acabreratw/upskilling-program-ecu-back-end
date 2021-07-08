package com.thoughtworks.lpe.be_template.dtos;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "user_course")
@Entity
public class UserCourseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    @Column(name = "id_course", nullable = false)
    private Integer courseId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    public UserCourseDto() {
    }

    public UserCourseDto(Integer courseId, CourseStatus status, String userEmail) {
        this.courseId = courseId;
        this.status = status;
        this.userEmail = userEmail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public CourseStatus getStatus() { return status; }

    public void setStatus(CourseStatus status) { this.status = status; }

    public String toString() {
      return "UserCourse{" +
        " id=" + id +
        ", course=" + courseId +
        ", status=" + status +
        ", username=" + userEmail +
        "}";
    }
}