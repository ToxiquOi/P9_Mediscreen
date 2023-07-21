package com.mediscreen.userdb;

import com.mediscreen.userdb.controller.UserController;
import com.mediscreen.userdb.domain.ESex;
import com.mediscreen.userdb.domain.User;
import com.mediscreen.userdb.exception.UserNotFoundException;
import com.mediscreen.userdb.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTest {

    @Mock
    UserService userService;


    @InjectMocks
    UserController userController;


    @Test
    void getAllTest() {
        when(userService.getAll())
                .thenReturn(new ArrayList<>());
        ResponseEntity<List<User>> rs = userController.getAll();
        Assertions.assertEquals(HttpStatus.OK, rs.getStatusCode());
        Assertions.assertNotNull(rs.getBody());
    }


    @Test
    void getByIdTest() {
        when(userService.getUser(anyInt()))
                .thenReturn(new User());

        ResponseEntity<User> rs = userController.getById(1);
        Assertions.assertNotNull(rs.getBody());
        Assertions.assertEquals(HttpStatus.OK, rs.getStatusCode());
    }

    @Test
    void getByFamilyNameTest() {

        when(userService.getUserByFamilyName(anyString()))
                .thenReturn(new User());

        ResponseEntity<User> rs = userController.getByFamilyName("Toto");
        Assertions.assertNotNull(rs.getBody());
        Assertions.assertEquals(HttpStatus.OK, rs.getStatusCode());
    }

    @Test
    void addUserTest1() {
        when(userService.addUser(any()))
                .thenReturn(new User());
        User u = new User();
        ResponseEntity<User> rs = userController.addUser(u);
        Assertions.assertNotNull(rs.getBody());
        Assertions.assertEquals(HttpStatus.OK, rs.getStatusCode());
    }

    @Test
    void addUserTest2() {
        when(userService.addUser(anyString(),anyString(),any(),anyString(),anyString()))
                .thenReturn(new User());
        ResponseEntity<User> rs = userController.addUser("toto","10-12-1992", ESex.M, "toto", "toto");
        Assertions.assertNotNull(rs.getBody());
        Assertions.assertEquals(HttpStatus.OK, rs.getStatusCode());
    }


    @Test
    void deleteUserTest1() {
        when(userService.deleteUser(anyInt()))
                .thenReturn(true);
        ResponseEntity<?> rs = userController.deleteUser(10);
        Assertions.assertEquals(HttpStatus.ACCEPTED, rs.getStatusCode());
    }

    @Test
    void deleteUserTest2() {
        when(userService.deleteUser(anyInt()))
                .thenReturn(false);
        ResponseEntity<?> rs = userController.deleteUser(666);
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, rs.getStatusCode());
    }

    @Test
    void userNotFoundHandlerTest() {
        ResponseEntity<String> rs =  userController.userNotFoundHandler(new UserNotFoundException("TEST"));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, rs.getStatusCode());
        Assertions.assertEquals("TEST", rs.getBody());
    }

}
