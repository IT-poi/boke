package com.cuit.boke.enums;

public enum DeleteFlagEnum {
    NORMAL(1, "正常"),//展示/系统操作
	DELETE(0, "已删除"),//不展示
	;

	private Integer key;
	private String value;

	DeleteFlagEnum(Integer key, String value) {
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
