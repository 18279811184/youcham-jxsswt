package io.youcham.common.constant;

/**
 * 状态；
 */
public enum ExamineStatus {
	/**
	 * 启用
	 */
	PASS(1,"通过"),
	/**
	 * 禁用
	 */
	NOT_PASS(0,"不通过");


	private final int value;
	
	private final String description;

	private ExamineStatus(int value,String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static ExamineStatus getByValue(Integer value) {
		if (null == value)
			return null;
		for (ExamineStatus _enum : ExamineStatus.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static ExamineStatus getByDesc(String description) {
		if (null == description)
			return null;
		for (ExamineStatus _enum : ExamineStatus.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}