package com.thoughtworks.lpe.be_template.services;

import com.thoughtworks.lpe.be_template.dtos.UserDto;
import com.thoughtworks.lpe.be_template.mappers.UserMapper;
import com.thoughtworks.lpe.be_template.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(UserDto userDto) {
        userRepository.save(UserMapper.dtoToDomain(userDto));
    }
}
