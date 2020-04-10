package io.youcham.common.constant;

/**
 * 性别类型；
 */
public enum SexEnum {
	/**
	 * 性别 	男
	 */
	MAN(2,"男"),
	/**
	 * 性别 	女
	 */
	WOMAN(1,"女"),
	/**
	 * 性别 	保密
	 */
	SECRECY(3,"保密");

	private final int value;
	
	private final String description;

	private SexEnum(int value,String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static SexEnum getDescByValue(Integer value) {
		if (null == value)
			return null;
		for (SexEnum _enum : SexEnum.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static SexEnum getOrganType(String description) {
		if (null == description)
			return null;
		for (SexEnum _enum : SexEnum.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}