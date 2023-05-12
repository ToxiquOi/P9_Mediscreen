package com.mediscreen.userdb.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
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
