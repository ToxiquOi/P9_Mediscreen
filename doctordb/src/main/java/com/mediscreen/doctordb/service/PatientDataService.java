package com.mediscreen.doctordb.service;

import com.mediscreen.doctordb.exception.DataNotFoundException;
import com.mediscreen.doctordb.model.PatientData;
import com.mediscreen.doctordb.repository.PatientDataRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientDataService {

    private final PatientDataRepository dataRepository;

    @Autowired
    public PatientDataService(PatientDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public PatientData addHistory(int id, String e) {
        var data = getPatientDataById(id);
        data.getPatientHistory().add(e);
        return dataRepository.save(data);
    }

    public PatientData save(PatientData pd) {
        return dataRepository.save(pd);
    }

    @SneakyThrows
    public PatientData getPatientDataById(int id) {
        var optData = dataRepository.findById(id);
        if(optData.isEmpty()) {
            throw new DataNotFoundException();
        }

        return optData.get();
    }
}
