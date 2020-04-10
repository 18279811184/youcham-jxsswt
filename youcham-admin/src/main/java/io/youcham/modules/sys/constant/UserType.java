package io.youcham.modules.sys.constant;

/**
 * 用户类型；
 */
public enum UserType {
	/**
	 * 班轮
	 */
	SHIP(1,"班轮"),
	/**
	 * 拖车
	 */
	CAR(2,"拖车"),
	/**
	 * 多式联运
	 */
	OTHER(3,"多式联运");


	private final int value;
	
	private final String description;

	private UserType(int value,String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static UserType getByValue(Integer value) {
		if (null == value)
			return null;
		for (UserType _enum : UserType.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static UserType getByDesc(String description) {
		if (null == description)
			return null;
		for (UserType _enum : UserType.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}