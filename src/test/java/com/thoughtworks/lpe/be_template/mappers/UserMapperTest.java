package com.thoughtworks.lpe.be_template.mappers;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.User;
import com.thoughtworks.lpe.be_template.domains.enums.UserType;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.dtos.UserDto;
import com.thoughtworks.lpe.be_template.util.UserData;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.thoughtworks.lpe.be_template.util.Constants.DATETIME_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {
    @Test
    public void shouldReturnUserFromGivenUserDto() {
        UserDto userDto = UserDto.builder().id("some_id")
                .name("Some name")
                .email("somemail@somedomain.com")
                .type(UserType.TRAINEE)
                .build();

        User expectedUser = User.builder().id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .type(userDto.getType())
                .build();

        User user = UserMapper.dtoToDomain(userDto);

        assertThat(user).isEqualToComparingFieldByField(expectedUser);
    }

}
