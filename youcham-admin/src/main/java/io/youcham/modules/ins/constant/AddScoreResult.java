package io.youcham.modules.ins.constant;

/**
 * 加分申请状态；
 */
public enum AddScoreResult {
	/**
	 * 未通过
	 */
	SUBMIT(0,"未通过"),
	/**
	 * 待审核
	 */
	EXAMINE(1,"待审核"),
	/**
	 * 待提交
	 */
	SUCCESS(2,"待提交"),
	/**
	 * 审核已通过
	 */
	FAIL(3,"已通过");



	private final int value;
	
	private final String description;

	private AddScoreResult(int value,String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static AddScoreResult getByValue(Integer value) {
		if (null == value)
			return null;
		for (AddScoreResult _enum : AddScoreResult.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static AddScoreResult getByDesc(String description) {
		if (null == description)
			return null;
		for (AddScoreResult _enum : AddScoreResult.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}