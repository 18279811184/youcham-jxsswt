package io.youcham.modules.sys.constant;

/**
 * 部门类型；
 */
public enum DepartLevel {
	/**
	 * 派驻纪检组
	 */
	INSPECTION(0,"派驻纪检组"),
	/**
	 * 监察室
	 */
	MONITOR(1,"监察室"),
	/**
	 * 监察部门
	 */
	DISCIPLINE(2,"纪检组");

	private final int value;
	
	private final String description;

	private DepartLevel(int value,String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static DepartLevel getDescByValue(Integer value) {
		if (null == value)
			return null;
		for (DepartLevel _enum : DepartLevel.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static DepartLevel getOrganType(String description) {
		if (null == description)
			return null;
		for (DepartLevel _enum : DepartLevel.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}