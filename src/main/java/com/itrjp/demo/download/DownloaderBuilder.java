package com.itrjp.demo.download;

/**
 * @author : renjp
 * @date : 2020-09-05 17:06
 **/
public class DownloaderBuilder {

    public static Downloader createDefaultDownloader() {
        return new HttpDownloader();
    }
}
