package com.mediscreen.analytics.service;


import com.mediscreen.analytics.config.AppProperties;
import com.mediscreen.analytics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorDBService {

    private final String SERVICE_URI;
    private final WebRequestService wrs;

    @Autowired
    public DoctorDBService(WebRequestService wrs, AppProperties props) {
        this.wrs = wrs;
        SERVICE_URI = props.getDoctorServiceURIString() + "/patHistory/";
    }

    public List<String> getHistoryById(int id) {
        var res = wrs.doGetRequest(SERVICE_URI+id, Data.class);
        if(res.isEmpty())
            return new ArrayList<>();

        return res.get().getHistory();
    }
}
