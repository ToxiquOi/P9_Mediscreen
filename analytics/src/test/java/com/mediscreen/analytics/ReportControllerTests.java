package com.mediscreen.analytics;

import com.mediscreen.analytics.controller.AnalyticsController;
import com.mediscreen.analytics.domain.DiabeteFactor;
import com.mediscreen.analytics.domain.DiabeteReport;
import com.mediscreen.analytics.domain.DiabeteState;
import com.mediscreen.analytics.model.Patient;
import com.mediscreen.analytics.service.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReportControllerTests {

    Pattern rsPattern = Pattern.compile("Patient: (.*[^ ])? (.*[^ ])? [(]age (\\d*[^)])?[)] diabetes assessment is: .*");

    DiabeteReport r;

    @Mock
    ReportService reportService;

    @InjectMocks
    AnalyticsController analyticsController;

    @BeforeEach()
    void beforeEach() {
        Patient p = new Patient();
        p.setFamily("test");
        p.setFirstname("test");
        p.setLastname("test");
        p.setSex("H");
        p.setDob(Date.from(LocalDateTime.of(1990,1,15,12,6).toInstant(ZoneOffset.UTC)));

        r = new DiabeteReport(p);
        r.setFactors(Arrays.stream(DiabeteFactor.values())
                .limit(6)
                .collect(Collectors.toList()));
        r.setDiagnostic(DiabeteState.DANGER);
    }

    @Test
    void getReportOfUserIdTest() {
        when(reportService.getReportUsingId(anyInt()))
                .thenReturn(r);
        ResponseEntity<String> rs = analyticsController.getReportOfUserId(1);
        Assertions.assertEquals(HttpStatus.OK, rs.getStatusCode());
        Assertions.assertNotNull(rs.getBody());
        Assertions.assertTrue(rsPattern.matcher(rs.getBody()).matches());
    }

    @Test
    void getReportOfUserFamilyTest() {
        when(reportService.getReportUsingFamilyName(anyString()))
                .thenReturn(r);
        ResponseEntity<String> rs = analyticsController.getReportOfUserFamilyName("test");
        Assertions.assertEquals(HttpStatus.OK, rs.getStatusCode());
        Assertions.assertNotNull(rs.getBody());
        Assertions.assertTrue(rsPattern.matcher(rs.getBody()).matches());
    }

    @Test
    void handleNotFoundTests() {
        ResponseEntity<String> rs =  analyticsController.notFoundExceptionHandler(new Exception("TEST"));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, rs.getStatusCode());
        Assertions.assertEquals("TEST", rs.getBody());
    }
}
