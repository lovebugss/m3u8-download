package com.itrjp.demo.download;

import com.itrjp.demo.content.Content;

/**
 * @author : renjp
 * @date : 2020-09-05 16:57
 **/
public interface Downloader {

    /**
     * 下载器
     *
     * @param url
     * @return
     */
    Content download(String url);

//    <T> Content<T> download(String path, Class<T> stringClass);
}
