package io.youcham.common.constant;

/**
 * 选择状态；
 */
public enum CheckStatus {
	/**
	 * 否
	 */
	NOT_CHECK(0,"否"),
	/**
	 * 是
	 */
	IS_CHECK(1,"是");


	private final int value;

	private final String description;

	private CheckStatus(int value, String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static CheckStatus getByValue(Integer value) {
		if (null == value)
			return null;
		for (CheckStatus _enum : CheckStatus.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static CheckStatus getByDesc(String description) {
		if (null == description)
			return null;
		for (CheckStatus _enum : CheckStatus.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}