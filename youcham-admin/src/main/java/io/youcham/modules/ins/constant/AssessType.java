package io.youcham.modules.ins.constant;

/**
 * 政治生态指标类型
 */
public enum AssessType {
	/**
	 * 评价项目
	 */
	PROJECT(0,"评价项目"),
	/**
	 * 评价内容
	 */
	CONTENT(1,"评价内容");


	private final int value;
	
	private final String description;

	private AssessType(int value,String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static AssessType getDescByValue(Integer value) {
		if (null == value)
			return null;
		for (AssessType _enum : AssessType.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static AssessType getOrganType(String description) {
		if (null == description)
			return null;
		for (AssessType _enum : AssessType.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}