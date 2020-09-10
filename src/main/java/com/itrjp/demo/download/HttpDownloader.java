package com.itrjp.demo.download;

import com.itrjp.demo.content.Content;
import com.itrjp.demo.content.StringContent;
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
 * @date : 2020-09-05 16:58
 **/
public class HttpDownloader implements Downloader {
    @Override
    public Content<String> download(String url) {

        HttpUriRequest getRequest = new HttpGet(url);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(getRequest)) {
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity);
            return new StringContent(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
