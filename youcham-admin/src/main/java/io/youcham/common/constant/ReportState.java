package io.youcham.common.constant;

/**
 * 状态；
 */
public enum ReportState {
	/**
	 * 未提交
	 */
	BEFORE(0,"未提交"),
	/**
	 * 已提交
	 */
	AFTER(1,"已提交");


	private final int value;
	
	private final String description;

	private ReportState(int value,String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static ReportState getDescByValue(Integer value) {
		if (null == value)
			return null;
		for (ReportState _enum : ReportState.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static ReportState getOrganType(String description) {
		if (null == description)
			return null;
		for (ReportState _enum : ReportState.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}