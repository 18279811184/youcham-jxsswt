package io.youcham.modules.ins.constant;

/**
 * 在线考试题型
 */
public enum QuestionType {
	/**
	 * 单选题
	 */
	SINGLE(0,"单选题"),
	/**
	 * 判断题
	 */
	JUDGE(1,"判断题"),
	/**
	 * 多选题
	 */
	MULTIPLE(2,"多选题");


	private final int value;
	
	private final String description;

	private QuestionType(int value,String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static QuestionType getByValue(Integer value) {
		if (null == value)
			return null;
		for (QuestionType _enum : QuestionType.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static QuestionType getByDesc(String description) {
		if (null == description)
			return null;
		for (QuestionType _enum : QuestionType.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}