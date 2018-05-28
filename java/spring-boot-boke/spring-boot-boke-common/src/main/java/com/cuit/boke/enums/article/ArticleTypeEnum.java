package com.cuit.boke.enums.article;

public enum  ArticleTypeEnum {

    ORIGINAL(0, "原创"),
    REPRINT(1, "转载"),
    TRANSLATE(-1, "翻译");

    private Integer key;
    private String value;

    ArticleTypeEnum(Integer key, String value) {
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
