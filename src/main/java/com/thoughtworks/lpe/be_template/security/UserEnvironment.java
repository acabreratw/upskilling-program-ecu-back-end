package com.thoughtworks.lpe.be_template.security;

import com.thoughtworks.lpe.be_template.config.UserData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEnvironment {
    private UserData userData;
}
