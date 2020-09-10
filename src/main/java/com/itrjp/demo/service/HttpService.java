package com.itrjp.demo.service;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author : renjp
 * @date : 2020-09-05 16:42
 **/
@Service
public class HttpService {

    public void sendRequest(String path) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpUriRequest getRequest = new HttpGet(path);
        CloseableHttpResponse response = httpClient.execute(getRequest);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        HttpEntity entity = response.getEntity();
        String s = EntityUtils.toString(entity);
        System.out.println(s);
        System.out.println(statusCode);
        System.out.println(response);
    }
}
