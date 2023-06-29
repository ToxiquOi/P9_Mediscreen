package com.mediscreen.analytics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;


@Getter
@Service
public class WebRequestService {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Perform a basic HTTP Post request on specified URI
     * @param uri request endpoint
     * @param data body of the request
     * @param cData The type of object returned by request
     * @return An optional of type T
     * @param <T>
     */
    public <T> Optional<T> doPostResquest(String uri, Object data, Class<T> cData) {
        return doPostResquest(URI.create(uri), data, cData);
    }

    public <T> Optional<T> doPostResquest(URI uri, Object data, Class<T> cData) {
        HttpPost request = new HttpPost(uri);
        return doResquestWithData(request, data, cData);
    }

    /**
     * Perform a basic HTTP Post request on specified URI
     * @param uri request endpoint
     * @param data body of the request
     * @param cData The type of object returned by request
     * @return An optional of type T
     * @param <T>
     */
    public <T> Optional<T> doPutResquest(String uri, Object data, Class<T> cData) {
        return doPutResquest(URI.create(uri), data, cData);
    }

    public <T> Optional<T> doPutResquest(URI uri, Object data, Class<T> cData) {
        HttpPut request = new HttpPut(uri);
        return doResquestWithData(request, data, cData);
    }

    /**
     * Perform a basic HTTP Get request on specified URI
     * @param uri request endpoint
     * @param responseType The type of object returned by request
     * @return An optional of type T
     * @param <T>
     */
    public <T> Optional<T> doGetRequest(String uri, Class<T> responseType) {
        HttpGet request = new HttpGet(uri);
        return executeRequest(request, responseType);
    }

    public <T> Optional<T> doGetRequest(URI uri, Class<T> responseType) {
        HttpGet request = new HttpGet(uri);
        return executeRequest(request, responseType);
    }



    /**
     * Execute Http request
     * Note: this can be moved into a utils class
     * @param request , The request to execute
     * @param tClass , The object type of request result
     * @return result of request
     * @param <T>
     */
    public <T> Optional<T> executeRequest(HttpUriRequestBase request, Class<T> tClass) {
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

    public <T> Optional<T> doResquestWithData(HttpUriRequestBase request, Object data, Class<T> cData) {
        this.setJsonHeader(request);
        if(data != null) {
            try {
                request.setEntity(new StringEntity(mapper.writeValueAsString(data)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return executeRequest(request, cData);
    }

    private void setJsonHeader(HttpUriRequestBase request) {
        request.setHeader("Content-type", "application/json");
    }
}
