package com.mediscreen.analytics.service;

import com.mediscreen.analytics.exception.PatientNotFoundException;
import com.mediscreen.analytics.model.Patient;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
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
            throw new PatientNotFoundException("Patient with id: " + id + " not exist");
        var patient = res.get();
        patient.setHistory(ddbservice.getHistoryById(patient.getId()));
        return res.get();
    }

    @SneakyThrows
    public Patient getPatientByFamilyName(String familyName) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("familyName", familyName));
        URI uri = new URIBuilder(URI.create(SERVICE_URI+"familyName/"))
                .addParameters(nameValuePairs)
                .build();

        var res = wrs.doGetRequest(uri, Patient.class);
        if(res.isEmpty())
            throw new PatientNotFoundException("Patient with familyname: " + familyName + " not exist");
        var patient = res.get();
        patient.setHistory(ddbservice.getHistoryById(patient.getId()));
        return res.get();
    }
}
