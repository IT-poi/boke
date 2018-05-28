package com.cuit.boke.enums.article;

public enum  ArticleStatusEnum {

    DRAFT(0, "草稿"),
    PUBLISHED(1, "已发布"),
    PRIVATE(2, "私密"),
    RECYCLE_BIN(-1, "回收站");

    private Integer key;
    private String value;

    ArticleStatusEnum(Integer key, String value) {
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
