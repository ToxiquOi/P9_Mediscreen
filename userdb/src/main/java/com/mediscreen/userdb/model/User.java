package com.mediscreen.userdb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class User {
    @Id
    private int id;

    private String firstname;

    private String lastname;

    private String family;

    private String given;

    private Date dob;

    private ESex sex;

    private String address;

    private String phone;
}
