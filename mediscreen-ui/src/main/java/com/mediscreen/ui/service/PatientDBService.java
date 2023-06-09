package com.mediscreen.ui.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.ui.exception.PatientNotFoundException;
import com.mediscreen.ui.exception.PatientSaveException;
import com.mediscreen.ui.model.PatientList;
import com.mediscreen.ui.domain.Patient;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientDBService {

    private final static String SERVICE_URI = "http://localhost:8081/patient/";

    private final ObjectMapper mapper = new ObjectMapper();

    public PatientDBService() {

    }

    @SneakyThrows
    public Patient getPatientById(int id) {

        HttpGet request = new HttpGet(SERVICE_URI+id);
        var res = executeRequest(request, Patient.class);
        if(res.isEmpty())
            throw new PatientNotFoundException();

        return res.get();
    }

    public List<Patient> getAllPatient() {
        HttpGet request = new HttpGet(SERVICE_URI);
        var res = executeRequest(request, PatientList.class);
        return res.isPresent() ? res.get() : new ArrayList<>();
    }

    @SneakyThrows
    public Patient savePatient(Patient p) {
        HttpPost request = new HttpPost(SERVICE_URI+"save");
        request.setHeader("Content-type", "application/json");
        try {
            request.setEntity(new StringEntity(mapper.writeValueAsString(p)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        var res = executeRequest(request, Patient.class);
        if (res.isEmpty())
            throw new PatientSaveException();
        return res.get();
    }

    public void deleteUser(Patient patient) {
        HttpDelete request = new HttpDelete(SERVICE_URI+patient.getId());
        var res = executeRequest(request, Object.class);

    }

    /**
     * Execute Http request
     * Note: this can be moved into a utils class
     * @param request , The request to execute
     * @param tClass , The object type of request result
     * @return result of request
     * @param <T>
     */
    private <T> Optional<T> executeRequest(HttpUriRequestBase request, Class<T> tClass) {
        T res = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            if(HttpStatus.SC_OK == response.getCode() && httpEntity != null) {
                res = mapper.readValue(EntityUtils.toString(httpEntity), tClass);
            }
        } catch (Exception e) {
            System.out.println("Error during process on Patient API: " + e);
        }

        return Optional.ofNullable(res);
    }

}
