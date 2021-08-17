package com.thoughtworks.lpe.be_template.domains;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "type", nullable = false)
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
