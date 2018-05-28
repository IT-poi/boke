package com.cuit.boke.enums;

public enum SexEnum {
    WOMAN(0, "女"),//展示/系统操作
	MAN(1, "男"),//不展示
	UNKNOW(-1, "保密"),
	;

	private Integer key;
	private String value;

	SexEnum(Integer key, String value) {
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
