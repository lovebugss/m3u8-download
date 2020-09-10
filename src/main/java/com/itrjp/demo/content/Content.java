package com.itrjp.demo.content;

/**
 * @author : renjp
 * @date : 2020-09-05 16:55
 **/
public interface Content<T> {
    /**
     * 获取内容
     *
     * @return
     */
    T getContent();
}
