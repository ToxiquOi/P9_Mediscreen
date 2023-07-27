package com.mediscreen.ui.model;

import java.util.HashMap;
import java.util.List;

public class Data extends HashMap<String, Object> {
    private static final String HIST_KEY = "patientHistory";
    public List<String> getHistory() {
        return (List<String>) get(HIST_KEY);
    }
}