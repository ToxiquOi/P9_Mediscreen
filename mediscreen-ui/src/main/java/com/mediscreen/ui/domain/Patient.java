package com.mediscreen.ui.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Patient {

    private int id;
    private String firstname;

    private String lastname;

    private String family;

    private String given;

    private Date dob;

    private String sex;

    private String address;

    private String phone;

    private List<String> history;
}
