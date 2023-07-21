package com.mediscreen.doctordb;

import com.mediscreen.doctordb.controller.PatientDataController;
import com.mediscreen.doctordb.repository.PatientDataRepository;
import com.mediscreen.doctordb.service.PatientDataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DoctordbApplicationTests {

    @Autowired
    private PatientDataController patientDataController;

    @Autowired
    private PatientDataRepository patientDataRepository;

    @Autowired
    private PatientDataService patientDataService;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(patientDataController);
        Assertions.assertNotNull(patientDataRepository);
        Assertions.assertNotNull(patientDataService);
    }

}
