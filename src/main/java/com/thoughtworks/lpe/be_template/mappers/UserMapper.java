package com.thoughtworks.lpe.be_template.mappers;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.User;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.dtos.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User dtoToDomain(UserDto userDto){
        return User.builder().id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .type(userDto.getType())
                .build();
    }

}
