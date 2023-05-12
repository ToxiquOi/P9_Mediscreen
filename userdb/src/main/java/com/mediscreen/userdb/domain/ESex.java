package com.mediscreen.userdb.domain;

import lombok.Getter;

@Getter
public enum ESex {
    M("M"),
    F("F");

    private String value;
    ESex(String value) {
        this.value = value;
    }
}
