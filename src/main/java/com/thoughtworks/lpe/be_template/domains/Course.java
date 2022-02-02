package com.thoughtworks.lpe.be_template.domains;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "trainer_id", nullable=false)
    private Trainer trainer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "category_id", nullable=false)
    private Category category;

    public Course(String name, String description, String imageUrl, LocalDateTime freeStartDate, LocalDateTime freeEndDate) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.freeStartDate = freeStartDate;
        this.freeEndDate = freeEndDate;
    }
}