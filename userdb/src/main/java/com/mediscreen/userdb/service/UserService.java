package com.mediscreen.userdb.service;

import com.mediscreen.userdb.domain.ESex;
import com.mediscreen.userdb.exception.UserNotFoundException;
import com.mediscreen.userdb.domain.User;
import com.mediscreen.userdb.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.Date;
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

    public User addUser(String family, String dob, ESex sex, String address, String phone) {
        String[] birthdate = dob.split("-");

        User user = new User();
        user.setFamily(family);
        user.setDob(new Date(Integer.parseInt(birthdate[0]), Integer.parseInt(birthdate[1])-1, Integer.parseInt(birthdate[2])));
        user.setSex(sex.getValue());
        user.setAddress(address);
        user.setPhone(phone);
        return userRepository.saveAndFlush(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public boolean deleteUser(int id) {
        try {
            User user = getUser(id);
            userRepository.deleteById(user.getId());
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @SneakyThrows
    public User getUserByFamilyName(String familyName) {
        var optUser = userRepository.findUserByFamily(familyName);
        if (optUser.isEmpty()) throw new UserNotFoundException("User with family name:"+ familyName + " not found");
        return optUser.get();
    }
}
