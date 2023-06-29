package com.mediscreen.analytics.service;

import com.mediscreen.analytics.exception.PatientNotFoundException;
import com.mediscreen.analytics.exception.PatientSaveException;
import com.mediscreen.analytics.model.Patient;
import com.mediscreen.analytics.model.PatientList;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientDBService {

    private final static String SERVICE_URI = "http://localhost:8081/patient/";
    private final WebRequestService wrs;
    private final DoctorDBService ddbservice;

    @Autowired
    public PatientDBService(WebRequestService webRequestService, DoctorDBService ddbservice) {
        this.wrs = webRequestService;
        this.ddbservice = ddbservice;
    }

    @SneakyThrows
    public Patient getPatientById(int id) {
        var res = wrs.doGetRequest(SERVICE_URI+id, Patient.class);
        if(res.isEmpty())
            throw new PatientNotFoundException();
        var patient = res.get();
        patient.setHistory(ddbservice.getHistoryById(patient.getId()));
        return res.get();
    }

    public List<Patient> getAllPatient() {
        var res = wrs.doGetRequest(SERVICE_URI, PatientList.class);
        return res.isPresent() ? res.get() : new ArrayList<>();
    }

    @SneakyThrows
    public Patient savePatient(Patient p) {
        var res = wrs.doPutResquest(SERVICE_URI+"save", p, Patient.class);
        if (res.isEmpty())
            throw new PatientSaveException();
        return res.get();
    }

    public void deleteUser(Patient patient) {
        HttpDelete request = new HttpDelete(SERVICE_URI+patient.getId());
        var res = wrs.executeRequest(request, Object.class);
    }



}
