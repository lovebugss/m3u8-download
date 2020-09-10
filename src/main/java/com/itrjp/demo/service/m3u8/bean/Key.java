package com.itrjp.demo.service.m3u8.bean;

/**
 * @author : renjp
 * @date : 2020-09-10 21:55
 **/
public class Key {
    private String method;
    private String uri;
    private String iv;

    public Key(String method, String uri, String iv) {
        this.method = method;
        this.uri = uri;
        this.iv = iv;
    }

    public Key() {
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    @Override
    public String toString() {
        return "Key{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", iv='" + iv + '\'' +
                '}';
    }
}
