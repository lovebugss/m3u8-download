package com.itrjp.demo.util;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author : renjp
 * @date : 2020-09-10 21:33
 **/
public class DownloadUtil {

    public static String downloadFile(String path) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpUriRequest getRequest = new HttpGet(path);
        CloseableHttpResponse response = httpClient.execute(getRequest);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode != 200) {
            throw new RuntimeException("download fail, response status code: " + statusCode);
        }
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }
}
