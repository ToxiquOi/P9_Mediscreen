package com.mediscreen.userdb.controller;

import com.mediscreen.userdb.domain.ESex;
import com.mediscreen.userdb.domain.User;
import com.mediscreen.userdb.exception.UserNotFoundException;
import com.mediscreen.userdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController("user")
@RequestMapping("/patient/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("/save")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestParam String family, @RequestParam String dob,
                                        @RequestParam ESex sex, @RequestParam String address,
                                        @RequestParam String phone) {
        return ResponseEntity.ok(userService.addUser(family,dob,sex,address,phone));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        var res = userService.deleteUser(id);
        if (res)
            return ResponseEntity.accepted().build();
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<String> UserNotFoundHandler(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
