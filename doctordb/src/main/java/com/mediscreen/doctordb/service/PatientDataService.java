package com.mediscreen.doctordb.service;

import com.mediscreen.doctordb.exception.DataNotFoundException;
import com.mediscreen.doctordb.model.PatientData;
import com.mediscreen.doctordb.repository.PatientDataRepository;
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
        PatientData data;
        try {
            data = getPatientDataById(id);
        } catch (DataNotFoundException ex) {
            data = new PatientData();
            data.setPatientId(id);
        }

        data.getPatientHistory().add(e);
        return save(data);
    }

    public PatientData save(PatientData pd) {
        return dataRepository.save(pd);
    }


    public PatientData getPatientDataById(int id) throws DataNotFoundException {
        var optData = dataRepository.findById(id);
        if(optData.isEmpty()) {
            throw new DataNotFoundException();
        }

        return optData.get();
    }
}
