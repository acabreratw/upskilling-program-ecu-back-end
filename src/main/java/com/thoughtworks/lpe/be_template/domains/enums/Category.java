package com.thoughtworks.lpe.be_template.domains.enums;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Category {
    @Id
    @Column(name = "id", insertable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name", nullable = false)
    private String category_name;

    public Category(Integer id, String category_name) {
        this.id = id;
        this.category_name = category_name;
    }
}
