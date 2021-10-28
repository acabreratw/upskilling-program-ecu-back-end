package com.thoughtworks.lpe.be_template.services;

import com.thoughtworks.lpe.be_template.domains.User;
import com.thoughtworks.lpe.be_template.domains.enums.UserType;
import com.thoughtworks.lpe.be_template.dtos.UserDto;
import com.thoughtworks.lpe.be_template.repositories.UserRepository;
import com.thoughtworks.lpe.be_template.util.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private final UserService userService = new UserService();

    private final TestData testData = new TestData();

    @Test
    public void shouldSaveGivenUser() {
        ArgumentCaptor<User> caughtUser = ArgumentCaptor.forClass(User.class);
        UserDto userDto = testData.getUserDto(UserType.TRAINEE);
        User expectedUser = new User(userDto.getId(), userDto.getEmail(), userDto.getName(), userDto.getType());

        userService.save(userDto);

        verify(userRepository).save(caughtUser.capture());
        assertThat(caughtUser.getValue()).isEqualTo(expectedUser);
    }


}