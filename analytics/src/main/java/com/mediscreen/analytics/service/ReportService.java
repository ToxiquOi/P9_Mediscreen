package com.mediscreen.analytics.service;

import com.mediscreen.analytics.domain.DiabeteFactor;
import com.mediscreen.analytics.domain.DiabeteReport;
import com.mediscreen.analytics.domain.DiabeteState;
import com.mediscreen.analytics.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ReportService {

    private final PatientDBService patientService;

    @Autowired
    public ReportService(PatientDBService patientService) {
        this.patientService = patientService;
    }

    public DiabeteReport getReportUsingId(int id) {
        Patient patient = patientService.getPatientById(id);
        DiabeteReport report = new DiabeteReport(patient);
        report.setFactors(collectFactors(patient));
        report.setDiagnostic(evaluateState(report));
        return report;
    }

    public DiabeteReport getReportUsingFamilyName(String familyName) {
        Patient patient = patientService.getPatientByFamilyName(familyName);
        DiabeteReport report = new DiabeteReport(patient);
        report.setFactors(collectFactors(patient));
        report.setDiagnostic(evaluateState(report));
        return report;
    }

    private List<DiabeteFactor> collectFactors(Patient patient) {
        return Arrays.stream(DiabeteFactor.values())
                .filter(f -> patient.getHistory()
                                .stream()
                                .anyMatch(s-> s.contains(f.getValue()))
                ).toList();
    }

    private DiabeteState evaluateState(DiabeteReport report) {
        int nbFactors = report.getFactors().size();
        long age = report.getAge();
        String sex = report.getPatient().getSex();

        if(nbFactors == 0) return DiabeteState.NONE;
        if(age >= 30) {
            switch (nbFactors) {
                case 2 -> {
                    return DiabeteState.BODERLINE;
                }
                case 6 -> {
                    return DiabeteState.DANGER;
                }
                case 8 -> {
                    return DiabeteState.EARLY;
                }
                default -> {}
            }
        }
        else {
            if(sex.equals("H")) {
                switch (nbFactors) {
                    case 3 -> {
                        return DiabeteState.DANGER;
                    }
                    case 5 -> {
                        return DiabeteState.EARLY;
                    }
                    default -> {}
                }
            } else {
                switch (nbFactors) {
                    case 4 -> {
                        return DiabeteState.DANGER;
                    }
                    case 7 -> {
                        return DiabeteState.EARLY;
                    }
                    default -> {}
                }
            }
        }
        return DiabeteState.NONE;
    }

}
