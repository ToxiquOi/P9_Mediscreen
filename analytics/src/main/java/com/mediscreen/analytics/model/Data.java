package com.mediscreen.analytics.model;

import java.util.HashMap;
import java.util.List;

public class Data extends HashMap<String, Object> {

    public int getId() {
        return (int) get("patientId");
    }

    public List<String> getHistory() {
        return (List<String>) get("patientHistory");
    }
    public void setHistory(List<String> history) {
        if(containsKey("patientHistory")) replace("patientHistory", history);
        else put("patientHistory", history);
    }
}
