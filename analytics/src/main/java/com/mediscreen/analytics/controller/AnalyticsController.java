package com.mediscreen.analytics.controller;

import com.mediscreen.analytics.domain.DiabeteReport;
import com.mediscreen.analytics.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnalyticsController {

    private final ReportService reportService;

    @Autowired
    public AnalyticsController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/assess/{id}")
    public ResponseEntity<String> getReportOfUserId(@PathVariable int id) {
        DiabeteReport report = reportService.getReportUsingId(id);
        return ResponseEntity.ok(String.format("Patient: %s %s (age %d) diabetes assessment is: %s",
                report.getPatient().getFirstname(),
                report.getPatient().getFamily(),
                report.getAge(),
                report.getDiagnostic().getValue()));
    }

    @GetMapping("/familyName")
    public ResponseEntity<String> getReportOfUserFamilyName(@RequestParam String familyName) {
        DiabeteReport report = reportService.getReportUsingFamilyName(familyName);
        return ResponseEntity.ok(String.format("Patient: %s %s (age %d) diabetes assessment is: %s",
                report.getPatient().getFirstname(),
                report.getPatient().getFamily(),
                report.getAge(),
                report.getDiagnostic().getValue()));
    }
}
