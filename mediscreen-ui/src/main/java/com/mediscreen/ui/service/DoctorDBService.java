package com.mediscreen.ui.service;

import com.mediscreen.ui.domain.Patient;
import com.mediscreen.ui.exception.HistorySaveException;
import com.mediscreen.ui.model.Data;
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

    @SneakyThrows
    public List<String> addHistory(Patient p) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("patId", ""+p.getId()));
        nameValuePairs.add(new BasicNameValuePair("e", p.getHistory().get(p.getHistory().size()-1)));

        URI uri = new URIBuilder(URI.create(SERVICE_URI+"add/"))
                .addParameters(nameValuePairs)
                .build();

        var res = wrs.doPostResquest(uri, null, Data.class);
        if (res.isEmpty())
            throw new HistorySaveException();

        return res.get().getHistory();
    }
}
