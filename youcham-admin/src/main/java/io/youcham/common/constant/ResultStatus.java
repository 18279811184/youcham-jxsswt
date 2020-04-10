package io.youcham.common.constant;

/**
 * 状态；
 */
public enum ResultStatus {
	/**
	 * 待提交
	 */
	SUBMIT(0,"待提交"),
	/**
	 * 待审核
	 */
	EXAMINE(1,"待审核"),
	/**
	 * 已通过
	 */
	SUCCESS(2,"已通过"),
	/**
	 * 审核不通过
	 */
	FAIL(3,"未通过");


	private final int value;
	
	private final String description;

	private ResultStatus(int value,String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static ResultStatus getByValue(Integer value) {
		if (null == value)
			return null;
		for (ResultStatus _enum : ResultStatus.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static ResultStatus getByDesc(String description) {
		if (null == description)
			return null;
		for (ResultStatus _enum : ResultStatus.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}