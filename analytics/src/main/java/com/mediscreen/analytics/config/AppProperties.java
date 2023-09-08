package com.mediscreen.analytics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppProperties {

    private final Environment env;

    @Autowired
    public AppProperties(Environment env) {
        this.env = env;
    }

    public String getDoctorServiceURIString() {
        return env.getProperty("service.doctor.uri");
    }

    public String getPatientServiceURIString() {
        return env.getProperty("service.patient.uri");
    }
}