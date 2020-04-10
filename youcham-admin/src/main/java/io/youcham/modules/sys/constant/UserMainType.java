package io.youcham.modules.sys.constant;

/**
 * 用户证件类型类型；
 */
public enum UserMainType {
	/**
	 * 身份证
	 */
	MANAGER("01","身份证"),
	/**
	 * 护照
	 */
	PASSPORT("01","护照"),
	/**
	 * 台湾同胞来往内地通行证
	 */
	TAIWAN("02","台湾同胞来往内地通行证"),
	/**
	 * 港澳居民来往内地通行证
	 */
	GAGNAO("03","港澳居民来往内地通行证"),
	/**
	 * 外国人居留证
	 */
	FOREIGN("04","外国人居留证");

	private final String value;

	private final String description;

	private UserMainType(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static UserMainType getByValue(String value) {
		if (null == value)
			return null;
		for (UserMainType _enum : UserMainType.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static UserMainType getByDesc(String description) {
		if (null == description)
			return null;
		for (UserMainType _enum : UserMainType.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}