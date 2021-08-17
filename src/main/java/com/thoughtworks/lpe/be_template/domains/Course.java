package com.thoughtworks.lpe.be_template.domains;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", insertable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "free_start_date")
    private LocalDateTime freeStartDate;

    @Column(name = "free_end_date")
    private LocalDateTime freeEndDate;

    public Course(String name, String description, String imageUrl, LocalDateTime freeStartDate, LocalDateTime freeEndDate) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.freeStartDate = freeStartDate;
        this.freeEndDate = freeEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Course course = (Course) o;

        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}