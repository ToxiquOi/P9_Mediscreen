package com.mediscreen.ui;

import com.mediscreen.ui.exception.PatientNotFoundException;
import com.mediscreen.ui.domain.Patient;
import com.mediscreen.ui.service.DoctorDBService;
import com.mediscreen.ui.service.PatientDBService;
import com.mediscreen.ui.service.WebRequestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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

}
