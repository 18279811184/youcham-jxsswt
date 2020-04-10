package io.youcham.modules.ins.constant;

/**
 * 处置方式枚举
 * 
 * @author Administrator
 *
 */
public enum DispositionMethod {
	/**
	 * 未提交
	 */
	NOTSUBMIT(0, "未提交"),
	/**
	 * 已提交
	 */
	SUBMIT(1, "已提交"),
	/**
	 * 谈话函询
	 */
	TALK(2, "谈话函询"),
	/**
	 * 初步核实
	 */
	VERIFY(3, "初步核实"),
	/**
	 * 暂存待查
	 */
	TS(4, "暂存待查"),
	/**
	 * 予以了结
	 */
	SETTLE(5, "予以了结");

	private final int value;

	private final String description;

	private DispositionMethod(int value, String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static DispositionMethod getDescByValue(Integer value) {
		if (null == value)
			return null;
		for (DispositionMethod _enum : DispositionMethod.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}

	public static DispositionMethod getOrganType(String description) {
		if (null == description)
			return null;
		for (DispositionMethod _enum : DispositionMethod.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}
