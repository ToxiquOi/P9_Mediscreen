package com.mediscreen.ui.service;
import com.mediscreen.ui.config.AppProperties;
import com.mediscreen.ui.exception.PatientNotFoundException;
import com.mediscreen.ui.exception.PatientSaveException;
import com.mediscreen.ui.model.PatientList;
import com.mediscreen.ui.domain.Patient;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientDBService {

    private final  String SERVICE_URI;
    private final WebRequestService wrs;
    private final DoctorDBService ddbservice;

    @Autowired
    public PatientDBService(WebRequestService webRequestService, DoctorDBService ddbservice, AppProperties props) {
        this.wrs = webRequestService;
        this.ddbservice = ddbservice;
        SERVICE_URI = props.getPatientServiceURIString() + "/patient/";
    }

    @SneakyThrows
    public Patient getPatientById(int id) {
        var res = wrs.doGetRequest(SERVICE_URI+id, Patient.class);
        if(res.isEmpty())
            throw new PatientNotFoundException("Patient with id: " + id + " not exist");
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
        var res = wrs.doPutRequest(SERVICE_URI+"save", p, Patient.class);
        if (res.isEmpty())
            throw new PatientSaveException();
        return res.get();
    }

    public void deleteUser(Patient patient) {
        HttpDelete request = new HttpDelete(SERVICE_URI+patient.getId());
        wrs.executeRequest(request, Object.class);
    }



}
