package com.mediscreen.analytics.domain;

import com.mediscreen.analytics.model.Patient;
import lombok.Getter;
import lombok.Setter;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DiabeteReport {

    private final Patient patient;

    private final long age;

    private List<DiabeteFactor> factors = new ArrayList<>();
    private DiabeteState diagnostic;


    public DiabeteReport(Patient patient) {
        this.patient = patient;
        LocalDateTime dob = Instant.ofEpochMilli(patient.getDob().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        age = Duration.between(dob, LocalDateTime.now()).toDays()/365;
    }


}
