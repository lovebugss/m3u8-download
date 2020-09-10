package com.itrjp.demo.service.m3u8.bean;

/**
 * @author : renjp
 * @date : 2020-09-10 21:55
 **/

public class Inf {
    private String duration;
    private String path;

    public Inf(String duration, String path) {
        this.duration = duration;
        this.path = path;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Inf{" +
                "duration=" + duration +
                ", path='" + path + '\'' +
                '}';
    }
}
