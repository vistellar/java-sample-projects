package org.weixing.spring.mybatis.domain;

/**
 * @author weixing
 */
public enum Level {
    NORMAL("0");

    private String value;

    Level(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
