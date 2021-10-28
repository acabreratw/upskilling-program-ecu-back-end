package com.thoughtworks.lpe.be_template.dtos;

import com.thoughtworks.lpe.be_template.domains.enums.UserType;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserDto {
    private String id;
    private String name;
    private String email;
    private UserType type;
}
