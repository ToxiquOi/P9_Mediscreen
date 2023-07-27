package com.mediscreen.analytics;

import com.mediscreen.analytics.exception.PatientNotFoundException;
import com.mediscreen.analytics.model.Patient;
import com.mediscreen.analytics.service.DoctorDBService;
import com.mediscreen.analytics.service.PatientDBService;
import com.mediscreen.analytics.service.WebRequestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PatientDBServiceTests {

    @Mock
    WebRequestService webRequestService;

    @Mock
    DoctorDBService doctorDBService;

    @InjectMocks
    PatientDBService patientDBService;

    @BeforeEach
    void beforeEach() {

    }

    @Test
    void getPatientByIdTest() {
        when(doctorDBService.getHistoryById(anyInt()))
                .thenReturn(new ArrayList<>());
        when(webRequestService.doGetRequest(anyString(), any(Class.class)))
                .thenReturn(Optional.of(new Patient()));

        Patient p = patientDBService.getPatientById(1);
        Assertions.assertNotNull(p);
        Assertions.assertNotNull(p.getHistory());
    }

    @Test
    void getPatientByIdThrowExeptionTest() {
        Assertions.assertThrows(PatientNotFoundException.class, () -> patientDBService.getPatientById(1));
    }

    @Test
    void getPatientByFamilyNameTest() {
        when(doctorDBService.getHistoryById(anyInt()))
                .thenReturn(new ArrayList<>());
        when(webRequestService.doGetRequest(any(URI.class), any(Class.class)))
                .thenReturn(Optional.of(new Patient()));

        Patient p = patientDBService.getPatientByFamilyName("test");
        Assertions.assertNotNull(p);
        Assertions.assertNotNull(p.getHistory());
    }

    @Test
    void getPatientByFamilyNameThrowExeptionTest() {
        Assertions.assertThrows(PatientNotFoundException.class, () -> patientDBService.getPatientByFamilyName("test"));
    }
}
