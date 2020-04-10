package io.youcham.common.constant;

/**
 * 状态；
 */
public enum StatusTossEnum {
	/**
	 * 提交
	 */
	DTJ(0,"待提交"),
	/**
	 * 提交
	 */
	YTJ(1,"已提交"),
	/**
	 * 待审核
	 */
	NPASS(2,"待审核"),
	/**
	 * 审核
	 */
	PASS(3,"已通过");


	private final int value;
	
	private final String description;

	private StatusTossEnum(int value,String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static StatusTossEnum getDescByValue(Integer value) {
		if (null == value)
			return null;
		for (StatusTossEnum _enum : StatusTossEnum.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static StatusTossEnum getOrganType(String description) {
		if (null == description)
			return null;
		for (StatusTossEnum _enum : StatusTossEnum.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}