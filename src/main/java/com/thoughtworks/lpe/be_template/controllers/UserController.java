package com.thoughtworks.lpe.be_template.controllers;

import com.thoughtworks.lpe.be_template.dtos.UserDto;
import com.thoughtworks.lpe.be_template.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody UserDto userDto) {
        userService.save(userDto);
    }
}
