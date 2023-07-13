package com.mediscreen.analytics.service;


import com.mediscreen.analytics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorDBService {

    private final static String SERVICE_URI = "http://localhost:8082/patHistory/";
    private final WebRequestService wrs;

    @Autowired
    public DoctorDBService(WebRequestService wrs) {
        this.wrs = wrs;
    }

    public List<String> getHistoryById(int id) {
        var res = wrs.doGetRequest(SERVICE_URI+id, Data.class);
        if(res.isEmpty())
            return new ArrayList<>();

        return res.get().getHistory();
    }
}
