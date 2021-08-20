package com.thoughtworks.lpe.be_template.domains;

import com.thoughtworks.lpe.be_template.domains.enums.UserType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table
@EqualsAndHashCode
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType type;
}
