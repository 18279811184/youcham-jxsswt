package io.youcham.modules.ins.constant;

/**
 * 提交状态
 */
public enum SubmitType {
	/**
	 * 未提交
	 * 提交状态(未提交是0，已提交（代办理）是1，已办理2，二次办理3,谈话函询的线路{4归档})
	 */
	Nosubmit(0,"是"),
	/**
	 * 已提交 代办理
	 */
	Yisubmit(1,"否"),
	/**
	 * 已办理
	 */
	Alreadyhandled(2,"否"),
	/**
	 * 二次办理  
	 */
	Twotimes(3,"否"),
	/**
	 * 归档
	 */
	Timefile(4,"否");
	

	


	private final int value;
	
	private final String description;

	private SubmitType(int value,String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static YesorNoType getDescByValue(Integer value) {
		if (null == value)
			return null;
		for (YesorNoType _enum : YesorNoType.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static YesorNoType getOrganType(String description) {
		if (null == description)
			return null;
		for (YesorNoType _enum : YesorNoType.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}