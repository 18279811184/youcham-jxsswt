package io.youcham.common.constant;

/**
 * 状态；
 */
public enum StatusEnum {
	/**
	 * 启用
	 */
	NORMAL(1,"启用"),
	/**
	 * 禁用
	 */
	PAUSE(0,"禁用");


	private final int value;
	
	private final String description;

	private StatusEnum(int value,String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static StatusEnum getDescByValue(Integer value) {
		if (null == value)
			return null;
		for (StatusEnum _enum : StatusEnum.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static StatusEnum getOrganType(String description) {
		if (null == description)
			return null;
		for (StatusEnum _enum : StatusEnum.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}