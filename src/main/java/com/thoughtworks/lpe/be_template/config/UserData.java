package com.thoughtworks.lpe.be_template.config;

import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    private String id;
    private String email;
    private String fullName;
    private Set<String> permissions;
}
