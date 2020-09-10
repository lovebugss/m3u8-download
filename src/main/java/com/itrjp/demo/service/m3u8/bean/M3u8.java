package com.itrjp.demo.service.m3u8.bean;


import java.util.List;

/**
 * @author : renjp
 * @date : 2020-09-10 21:54
 **/
public class M3u8 {
    private int version;
    private int mediaSequence;
    private int targetDuration;
    private Key key;
    private boolean endList;
    private List<Inf> infList;

    public M3u8() {
    }

    public M3u8(int version, int mediaSequence, int targetDuration, Key key, boolean endList, List<Inf> infList) {
        this.version = version;
        this.mediaSequence = mediaSequence;
        this.targetDuration = targetDuration;
        this.key = key;
        this.endList = endList;
        this.infList = infList;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getMediaSequence() {
        return mediaSequence;
    }

    public void setMediaSequence(int mediaSequence) {
        this.mediaSequence = mediaSequence;
    }

    public int getTargetDuration() {
        return targetDuration;
    }

    public void setTargetDuration(int targetDuration) {
        this.targetDuration = targetDuration;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public boolean isEndList() {
        return endList;
    }

    public void setEndList(boolean endList) {
        this.endList = endList;
    }

    public List<Inf> getInfList() {
        return infList;
    }

    public void setInfList(List<Inf> infList) {
        this.infList = infList;
    }

    @Override
    public String toString() {
        return "M3u8{" +
                "version=" + version +
                ", mediaSequence=" + mediaSequence +
                ", targetDuration=" + targetDuration +
                ", key=" + key +
                ", endList=" + endList +
                ", infList=" + infList +
                '}';
    }
}
