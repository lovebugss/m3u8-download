package com.itrjp.demo.service;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author : renjp
 * @date : 2020-09-05 17:08
 **/
@Service
public class M3U8Service {

    public void getM3u8Content(String path) {
        String[] split1 = path.split("/");
        String prefix = path.replaceAll(split1[split1.length - 1], "");
        System.out.println(prefix);
        String data = download(path);
        Map<String, List<String[]>> result = parseContent(data);
        List<String[]> extinf = result.get("EXTINF");
        List<String[]> key = result.get("EXT-X-KEY");
        System.out.println(Arrays.toString(key.get(0)));

        List<String> filenames = extinf.stream()
                .map(strings -> strings[1])
                .map(s -> {
                    String[] split = s.split(",");
                    return split[split.length - 1];
                })
                .collect(Collectors.toList());
        System.out.println(filenames);

        List<CompletableFuture<String>> collect = filenames.stream()
                .map(filename ->
                        CompletableFuture.supplyAsync(() -> downloadFile(prefix + filename))
                )
                .collect(Collectors.toList());
        List<String> collect1 = collect.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        System.out.println(collect);

    }

    public String downloadFile(String path) {
        download(path, "./tmp");
        return path + " -> ok!";
    }

    public Map<String, List<String[]>> parseContent(String content) {
        System.out.println("正在解析...");
        if (content == null || !content.startsWith("#EXTM3U")) {
            throw new IllegalArgumentException("m3u8格式无效");
        }

        content = content.replaceAll("\n", "");
        String[] split = content.split("#");
        List<String> strings = Arrays.asList(split);
        System.out.println(strings);
        Map<String, List<String[]>> collect = strings.stream()
                .filter(StringUtils::hasLength)
                .map(s -> s.split(":"))
                .peek(s -> {
                    String s1 = Arrays.toString(s);
                    System.out.println(s1);
                })
                .collect(Collectors.groupingBy(strings1 -> strings1[0]));


        System.out.println(collect);
        return collect;
    }

    /**
     * 获取index文件
     *
     * @param path
     * @return
     */
    private String download(String path) {
        System.out.printf("开始下载: %s%n", path);
        HttpUriRequest getRequest = new HttpGet(path);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(getRequest)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void download(String url, String path) {
        System.out.printf("开始下载: %s%n", url);
        String[] split = url.split("/");

        path = path + "/" + split[split.length - 2];
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        path = path + "/" + split[split.length - 1];
        System.out.println("path: " + path);
        HttpUriRequest getRequest = new HttpGet(url);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(getRequest)) {
            HttpEntity entity = response.getEntity();
            try (FileOutputStream fileOutputStream = new FileOutputStream( new File(path))) {
                entity.writeTo(fileOutputStream);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
