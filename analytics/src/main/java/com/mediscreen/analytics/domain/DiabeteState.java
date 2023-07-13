package com.mediscreen.analytics.domain;

import lombok.Getter;

@Getter
public enum DiabeteState {
    NONE("None"),
    BODERLINE("Borderline"),
    DANGER("Danger"),
    EARLY("Early onset");

    public final String value;
    DiabeteState(String s) {
        value = s;
    }
}
