package com.mediscreen.userdb;

import com.mediscreen.userdb.domain.ESex;
import com.mediscreen.userdb.domain.User;
import com.mediscreen.userdb.exception.UserNotFoundException;
import com.mediscreen.userdb.repository.UserRepository;
import com.mediscreen.userdb.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @Order(0)
    void testCreateUser0() {
        User u = userService.addUser(new User());
        Assertions.assertNotNull(u);
        Assertions.assertEquals(userRepository.count(),u.getId());
    }

    @Test
    @Order(1)
    public void testAddUser1() {
        User u = userService.addUser("test","10-12-1997", ESex.M, "test", "test");
        Assertions.assertNotNull(u);
        Assertions.assertEquals(userRepository.count(),u.getId());
    }

    @Test
    @Order(2)
    void testGetUser2() {
        testCreateUser0();
        User u = userService.getUser((int)userRepository.count());
        Assertions.assertNotNull(u);
        Assertions.assertEquals(userRepository.count(),u.getId());
    }

    @Test
    @Order(3)
    void testGetUserThrowNotFound3() {
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUser(666));
    }

    @Test
    @Order(4)
    void testGetUserByFamilyName4() {
        testAddUser1();
        User u = userService.getUserByFamilyName("test");
        Assertions.assertNotNull(u);
        Assertions.assertEquals("test",u.getFamily());
    }

    @Test
    @Order(5)
    void testGetUserByFamilyNameThrowNotFound5() {
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserByFamilyName("azerty"));
    }
}
