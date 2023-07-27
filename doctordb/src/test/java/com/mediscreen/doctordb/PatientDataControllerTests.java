package com.mediscreen.doctordb;

import com.mediscreen.doctordb.controller.PatientDataController;
import com.mediscreen.doctordb.exception.DataNotFoundException;
import com.mediscreen.doctordb.model.PatientData;
import com.mediscreen.doctordb.service.PatientDataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PatientDataControllerTests {

    @Mock
    PatientDataService dataService;

    @InjectMocks
    PatientDataController dataController;


    @Test
    void getPatientDataByIdTest() throws DataNotFoundException {
        when(dataService.getPatientDataById(anyInt()))
                .thenReturn(new PatientData());

        ResponseEntity<PatientData> rs = dataController.getPatientDataById(1);
        Assertions.assertNotNull(rs.getBody());
        Assertions.assertEquals(HttpStatus.OK, rs.getStatusCode());
    }

    @Test
    void addPatientData() {
        when(dataService.addHistory(anyInt(), anyString()))
                .thenReturn(new PatientData());

        ResponseEntity<PatientData> rs = dataController.addHistory(1, "test");
        Assertions.assertNotNull(rs.getBody());
        Assertions.assertEquals(HttpStatus.OK, rs.getStatusCode());
    }

    @Test
    void dataNotFoundHandlerTest() {
        ResponseEntity<String> rs =  dataController.dataNotFoundExceptionHandler(new DataNotFoundException("TEST"));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, rs.getStatusCode());
        Assertions.assertEquals("TEST", rs.getBody());
    }
}
