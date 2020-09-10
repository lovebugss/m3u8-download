package com.itrjp.demo;

import com.itrjp.demo.service.HttpService;
import com.itrjp.demo.service.M3U8Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author : renjp
 * @date : 2020-09-05 16:43
 **/
@SpringBootTest
public class HttpServiceTest {

    @Autowired
    M3U8Service m3U8Service;

    @Test
    public void testSendRequest() throws IOException {
        m3U8Service.getM3u8Content("http://renjp-s3-test.s3.amazonaws.com/videos/gnzw/output.m3u8");
    }
}
