package com.cuit.boke.enums.article;

public enum ArticleTopEnum {
    TOP(1, "置顶"),
    NOT_TOP(0, "取消置顶");

    private Integer key;
    private String value;

    ArticleTopEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
