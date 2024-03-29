package com.mediscreen.analytics.service;

import com.mediscreen.analytics.config.AppProperties;
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

    private final String serviceURI;
    private final WebRequestService wrs;
    private final DoctorDBService ddbservice;

    @Autowired
    public PatientDBService(WebRequestService webRequestService, DoctorDBService ddbservice, AppProperties props) {
        this.wrs = webRequestService;
        this.ddbservice = ddbservice;
        serviceURI = props.getPatientServiceURIString() + "/patient/";
    }

    @SneakyThrows
    public Patient getPatientById(int id) {
        var res = wrs.doGetRequest(serviceURI +id, Patient.class);
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
        URI uri = new URIBuilder(URI.create(serviceURI +"familyName/"))
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
