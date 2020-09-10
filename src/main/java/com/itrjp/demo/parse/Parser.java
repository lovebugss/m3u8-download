package com.itrjp.demo.parse;

import com.itrjp.demo.content.Content;

/**
 * @author : renjp
 * @date : 2020-09-05 16:53
 **/
public interface Parser {
    /**
     * 解析器
     *
     * @param content 内容
     */
    void parser(Content content);
}
