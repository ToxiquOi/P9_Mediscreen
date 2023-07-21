package com.mediscreen.analytics.controller;

import com.mediscreen.analytics.domain.DiabeteReport;
import com.mediscreen.analytics.exception.HistoryNotFoundException;
import com.mediscreen.analytics.exception.PatientNotFoundException;
import com.mediscreen.analytics.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnalyticsController {
    private static final String REPORT_FORMAT_STRING = "Patient: %s %s (age %d) diabetes assessment is: %s";
    private final ReportService reportService;

    @Autowired
    public AnalyticsController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/assess/{id}")
    public ResponseEntity<String> getReportOfUserId(@PathVariable int id) {
        DiabeteReport report = reportService.getReportUsingId(id);
        return ResponseEntity.ok(String.format(REPORT_FORMAT_STRING,
                report.getPatient().getFirstname(),
                report.getPatient().getFamily(),
                report.getAge(),
                report.getDiagnostic().getValue()));
    }

    @GetMapping("/familyName")
    public ResponseEntity<String> getReportOfUserFamilyName(@RequestParam String familyName) {
        DiabeteReport report = reportService.getReportUsingFamilyName(familyName);
        return ResponseEntity.ok(String.format(REPORT_FORMAT_STRING,
                report.getPatient().getFirstname(),
                report.getPatient().getFamily(),
                report.getAge(),
                report.getDiagnostic().getValue()));
    }

    @ExceptionHandler({HistoryNotFoundException.class, PatientNotFoundException.class})
    public ResponseEntity<String> NotFoundExceptionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
