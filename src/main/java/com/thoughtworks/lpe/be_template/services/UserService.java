package com.thoughtworks.lpe.be_template.services;

import com.thoughtworks.lpe.be_template.domains.User;
import com.thoughtworks.lpe.be_template.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }
}
