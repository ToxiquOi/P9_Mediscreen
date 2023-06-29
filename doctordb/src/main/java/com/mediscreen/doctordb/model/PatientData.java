package com.mediscreen.doctordb.model;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("patients_datas")
public class PatientData {
    @Id
    private Integer patientId;

    private List<String> patientHistory=new ArrayList<>();
}
