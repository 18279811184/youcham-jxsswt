package io.youcham.modules.ins.constant;

/**
 * 是否监察对象
 */
public enum YesorNoType {
	/**
	 * 是
	 */
	YES(0,"是"),
	/**
	 * 否
	 */
	NO(1,"否");

	


	private final int value;
	
	private final String description;

	private YesorNoType(int value,String description) {
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