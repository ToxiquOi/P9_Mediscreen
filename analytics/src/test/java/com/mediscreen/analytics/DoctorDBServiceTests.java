package com.mediscreen.analytics;

import com.mediscreen.analytics.config.AppProperties;
import com.mediscreen.analytics.model.Data;
import com.mediscreen.analytics.service.DoctorDBService;
import com.mediscreen.analytics.service.WebRequestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class DoctorDBServiceTests {
    @Mock
    AppProperties props;
    
    @Mock
    WebRequestService webRequestService;

    @InjectMocks
    DoctorDBService doctorDBService;

    @Test
    void getPatientDataByIdTest() {
        Data d = new Data();
        d.put(Data.HIST_KEY, new ArrayList<String>());
        when(webRequestService.doGetRequest(anyString(), any(Class.class)))
                .thenReturn(Optional.of(d));

        List<String> h = doctorDBService.getHistoryById(1);
        Assertions.assertNotNull(h);
    }

    @Test
    void getPatientDataByIdNotExistTest() {
        when(webRequestService.doGetRequest(anyString(), any(Class.class)))
                .thenReturn(Optional.empty());

        List<String> h = doctorDBService.getHistoryById(1);
        Assertions.assertNotNull(h);
    }
}
