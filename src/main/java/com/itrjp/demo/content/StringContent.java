package com.itrjp.demo.content;

/**
 * @author : renjp
 * @date : 2020-09-05 16:59
 **/
public class StringContent implements Content<String> {

    private String data;

    public StringContent(String data) {
        this.data = data;
    }

    @Override
    public String getContent() {
        return data;
    }
}
