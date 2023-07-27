package com.mediscreen.analytics;

import com.mediscreen.analytics.controller.AnalyticsController;
import com.mediscreen.analytics.service.DoctorDBService;
import com.mediscreen.analytics.service.PatientDBService;
import com.mediscreen.analytics.service.ReportService;
import com.mediscreen.analytics.service.WebRequestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnalyticsApplicationTests {


    WebRequestService webRequestService;

    PatientDBService patientDBService;

    DoctorDBService doctorDBService;

    AnalyticsController analyticsController;

    ReportService reportService;

    @Autowired
    public AnalyticsApplicationTests(WebRequestService webRequestService, PatientDBService patientDBService, DoctorDBService doctorDBService, AnalyticsController analyticsController, ReportService reportService) {
        this.webRequestService = webRequestService;
        this.patientDBService = patientDBService;
        this.doctorDBService = doctorDBService;
        this.analyticsController = analyticsController;
        this.reportService = reportService;
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(webRequestService);
        Assertions.assertNotNull(patientDBService);
        Assertions.assertNotNull(doctorDBService);
        Assertions.assertNotNull(analyticsController);
        Assertions.assertNotNull(reportService);
    }

}
