package com.mediscreen.analytics;

import com.mediscreen.analytics.domain.DiabeteFactor;
import com.mediscreen.analytics.domain.DiabeteReport;
import com.mediscreen.analytics.domain.DiabeteState;
import com.mediscreen.analytics.model.Patient;
import com.mediscreen.analytics.service.PatientDBService;
import com.mediscreen.analytics.service.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReportServiceTests {

    @Mock
    PatientDBService patientDBService;

    @InjectMocks
    ReportService reportService;


    List<String> historyBODERLINE30 = Arrays.stream(DiabeteFactor.values())
            .map(DiabeteFactor::getValue)
            .limit(2)
            .collect(Collectors.toList());

    List<String> historyDANGER30 = Arrays.stream(DiabeteFactor.values())
            .map(DiabeteFactor::getValue)
            .limit(6)
            .collect(Collectors.toList());

    List<String> historyEARLY30 = Arrays.stream(DiabeteFactor.values())
            .map(DiabeteFactor::getValue)
            .limit(8)
            .collect(Collectors.toList());

    List<String> historyDANGERH = Arrays.stream(DiabeteFactor.values())
            .map(DiabeteFactor::getValue)
            .limit(3)
            .collect(Collectors.toList());

    List<String> historyEARLYH = Arrays.stream(DiabeteFactor.values())
            .map(DiabeteFactor::getValue)
            .limit(5)
            .collect(Collectors.toList());

    List<String> historyDANGERW = Arrays.stream(DiabeteFactor.values())
            .map(DiabeteFactor::getValue)
            .limit(4)
            .collect(Collectors.toList());

    List<String> historyEARLYW = Arrays.stream(DiabeteFactor.values())
            .map(DiabeteFactor::getValue)
            .limit(7)
            .collect(Collectors.toList());

    List<String> historyNONE = new ArrayList<>();
    List<String> historyNONEDefault = Arrays.stream(DiabeteFactor.values())
            .map(DiabeteFactor::getValue)
            .limit(11)
            .collect(Collectors.toList());

    @Test
    void testCollectFactors() {
        Patient p = new Patient();
        List<String> history = Arrays.stream(DiabeteFactor.values())
                .map(DiabeteFactor::getValue)
                .collect(Collectors.toList());

        p.setHistory(history);

        List<DiabeteFactor> factors = reportService.collectFactors(p);
        Assertions.assertEquals(history.size(), factors.size());
    }

    @Test
    void testEvaluateStateNONE() {
        Patient p = getPatient30();
        p.setHistory(historyNONE);
        DiabeteReport r = new DiabeteReport(p);
        r.setFactors(reportService.collectFactors(p));
        Assertions.assertEquals(DiabeteState.NONE, reportService.evaluateState(r));
    }

    @Test
    void testEvaluateStateNONEDefaut() {
        Patient p = getPatient30();
        p.setHistory(historyNONEDefault);
        DiabeteReport r = new DiabeteReport(p);
        r.setFactors(reportService.collectFactors(p));
        Assertions.assertEquals(DiabeteState.NONE, reportService.evaluateState(r));
    }

    @Test
    void testEvaluateStateBORDERLINE30() {
        Patient p = getPatient30();
        p.setHistory(historyBODERLINE30);
        DiabeteReport r = new DiabeteReport(p);
        r.setFactors(reportService.collectFactors(p));
        Assertions.assertEquals(DiabeteState.BODERLINE, reportService.evaluateState(r));
    }

    @Test
    void testEvaluateStateDANGER30() {
        Patient p = getPatient30();
        p.setHistory(historyDANGER30);
        DiabeteReport r = new DiabeteReport(p);
        r.setFactors(reportService.collectFactors(p));
        Assertions.assertEquals(DiabeteState.DANGER, reportService.evaluateState(r));
    }

    @Test
    void testEvaluateStateEARLY30() {
        Patient p = getPatient30();
        p.setHistory(historyEARLY30);
        DiabeteReport r = new DiabeteReport(p);
        r.setFactors(reportService.collectFactors(p));
        Assertions.assertEquals(DiabeteState.EARLY, reportService.evaluateState(r));
    }

    @Test
    void testEvaluateStateDANGERH() {
        Patient p = getPatientH();
        p.setHistory(historyDANGERH);
        DiabeteReport r = new DiabeteReport(p);
        r.setFactors(reportService.collectFactors(p));
        Assertions.assertEquals(DiabeteState.DANGER, reportService.evaluateState(r));
    }

    @Test
    void testEvaluateStateEARLYH() {
        Patient p = getPatientH();
        p.setHistory(historyEARLYH);
        DiabeteReport r = new DiabeteReport(p);
        r.setFactors(reportService.collectFactors(p));
        Assertions.assertEquals(DiabeteState.EARLY, reportService.evaluateState(r));
    }

    @Test
    void testEvaluateStateDANGERW() {
        Patient p = getPatientW();
        p.setHistory(historyDANGERW);
        DiabeteReport r = new DiabeteReport(p);
        r.setFactors(reportService.collectFactors(p));
        Assertions.assertEquals(DiabeteState.DANGER, reportService.evaluateState(r));
    }

    @Test
    void testEvaluateStateEARLYW() {
        Patient p = getPatientW();
        p.setHistory(historyEARLYW);
        DiabeteReport r = new DiabeteReport(p);
        r.setFactors(reportService.collectFactors(p));
        Assertions.assertEquals(DiabeteState.EARLY, reportService.evaluateState(r));
    }

    @Test
    void getReportUsingIdTest() {
        Patient p = getPatientW();
        p.setHistory(Arrays.stream(DiabeteFactor.values())
                .limit(8)
                .map(DiabeteFactor::getValue)
                .collect(Collectors.toList()));
        when(patientDBService.getPatientById(anyInt()))
                .thenReturn(p);

        DiabeteReport r = reportService.getReportUsingId(1);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(8, r.getFactors().size());
        Assertions.assertEquals(DiabeteState.NONE, r.getDiagnostic());
    }

    @Test
    void getReportUsingFamilyTest() {
        Patient p = getPatientH();
        p.setHistory(Arrays.stream(DiabeteFactor.values())
                .limit(2)
                .map(DiabeteFactor::getValue)
                .collect(Collectors.toList()));
        when(patientDBService.getPatientById(anyInt()))
                .thenReturn(p);

        DiabeteReport r = reportService.getReportUsingId(1);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(2, r.getFactors().size());
        Assertions.assertEquals(DiabeteState.NONE, r.getDiagnostic());
    }

    Patient getPatient30() {
        Patient p = new Patient();
        p.setSex("H");
        p.setDob(Date.from(LocalDateTime.of(1990,1,15,12,6).toInstant(ZoneOffset.UTC)));
        return p;
    }

    Patient getPatientW() {
        Patient p = new Patient();
        p.setSex("F");
        p.setDob(new Date());
        return p;
    }

    Patient getPatientH() {
        Patient p = new Patient();
        p.setSex("H");
        p.setDob(new Date());
        return p;
    }
}
