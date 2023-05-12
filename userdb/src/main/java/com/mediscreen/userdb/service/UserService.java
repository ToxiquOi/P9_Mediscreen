package com.mediscreen.userdb.service;

import com.mediscreen.userdb.exception.UserNotFoundException;
import com.mediscreen.userdb.domain.User;
import com.mediscreen.userdb.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SneakyThrows
    public User getUser(int id) {
        var optUser = userRepository.findById(id);
        if (optUser.isEmpty()) throw new UserNotFoundException("User with id:"+ id + " not found");
        return optUser.get();
    }

    public User addUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
