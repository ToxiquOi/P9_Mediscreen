package com.mediscreen.doctordb;

import com.mediscreen.doctordb.exception.DataNotFoundException;
import com.mediscreen.doctordb.model.PatientData;
import com.mediscreen.doctordb.repository.PatientDataRepository;
import com.mediscreen.doctordb.service.PatientDataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PatientDataServiceTest {

    @Autowired
    PatientDataRepository dataRepository;

    @Autowired
    PatientDataService dataService;

    @BeforeEach
    void beforeEach() {
        dataRepository.deleteAll();
    }

    @Test
    void getPatientDataTest3() throws DataNotFoundException {
        PatientData add = new PatientData();
        add.setPatientId(42);
        dataService.save(add);

        PatientData data = dataService.getPatientDataById(42);
        Assertions.assertNotNull(data);
        Assertions.assertEquals(42, data.getPatientId());
    }

    @Test
    void savePatientDataTest1() {
        PatientData data = new PatientData();
        data.setPatientId(1);
        data = dataService.save(data);
        Assertions.assertNotNull(data);
        Assertions.assertEquals(1, dataRepository.count());
    }

    @Test
    void addPatientDataTest2() {
        PatientData data = dataService.addHistory(1,"toto");
        Assertions.assertNotNull(data);
        Assertions.assertEquals(1, data.getPatientHistory().size());
    }

    @Test
    void getPatientDataTest4() {
        Assertions.assertThrows(DataNotFoundException.class, ()-> dataService.getPatientDataById(666));
    }

}
