package io.youcham.modules.ins.constant;

/**
 * 监督对象类型
 */
public enum SuperviseType {
	/**
	 * 监督个人
	 */
	PERSON(0,"监督个人"),
	/**
	 * 监督机构
	 */
	UNIT(1,"监督机构"),
	/**
	 * 监督机构
	 */
	PERSON_CODE(2,"个人代码"),
	/**
	 * 监督机构
	 */
	UNIT_CODE(3,"机构代码");
	


	private final int value;
	
	private final String description;

	private SuperviseType(int value,String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static SuperviseType getDescByValue(Integer value) {
		if (null == value)
			return null;
		for (SuperviseType _enum : SuperviseType.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static SuperviseType getOrganType(String description) {
		if (null == description)
			return null;
		for (SuperviseType _enum : SuperviseType.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}