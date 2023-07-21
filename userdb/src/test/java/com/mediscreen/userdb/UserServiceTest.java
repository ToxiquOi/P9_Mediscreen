package com.mediscreen.userdb;

import com.mediscreen.userdb.domain.User;
import com.mediscreen.userdb.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    void testCreateUser() {
        User u = userService.addUser(new User());
        Assertions.assertNotNull(u);
        Assertions.assertEquals(1,u.getId());
    }

}
