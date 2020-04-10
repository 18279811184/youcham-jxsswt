package io.youcham.modules.ins.constant;

/**
 * 政治生态指标评分类型
 */
public enum ScoreType {
	/**
	 * 加分
	 */
	EXTRA(0,"加分"),
	/**
	 * 减分
	 */
	DEDUCTION(1,"减分");


	private final int value;
	
	private final String description;

	private ScoreType(int value,String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static ScoreType getDescByValue(Integer value) {
		if (null == value)
			return null;
		for (ScoreType _enum : ScoreType.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static ScoreType getOrganType(String description) {
		if (null == description)
			return null;
		for (ScoreType _enum : ScoreType.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}