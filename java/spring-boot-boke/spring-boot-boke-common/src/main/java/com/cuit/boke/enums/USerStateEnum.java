package com.cuit.boke.enums;

public enum USerStateEnum {
    ENABLE(0, "启用"),//展示/系统操作
	NOTENABLE(1, "未启用"),//不展示
	DISABLE(2, "停用"),
	;

	private Integer key;
	private String value;

	USerStateEnum(Integer key, String value) {
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
