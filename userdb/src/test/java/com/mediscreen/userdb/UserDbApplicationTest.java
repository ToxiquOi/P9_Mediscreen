package com.mediscreen.userdb;

import com.mediscreen.userdb.controller.UserController;
import com.mediscreen.userdb.repository.UserRepository;
import com.mediscreen.userdb.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDbApplicationTest {


    @Autowired
    private UserController userController;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public UserDbApplicationTest() {

    }

    @Test
    void contextLoad() {
        Assertions.assertNotNull(userRepository);
        Assertions.assertNotNull(userService);
        Assertions.assertNotNull(userController);
    }
}
