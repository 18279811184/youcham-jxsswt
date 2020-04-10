package io.youcham.common.constant;

/**
 * 账款审核枚举
 * 
 * @author Administrator
 *
 */
public enum AccountAuditEnum {
	/**
	 * 未计费
	 */
	UNCOMMITTED(0, "提交"),
	/**
	 * 审核中
	 */
	AUDIT(1, "审核中"),
	/**
	 * 已完成
	 */
	FINISH(2, "已完成");
	
	
	private final Integer value;

	private final String description;

	private AccountAuditEnum(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	public Integer getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static AccountAuditEnum getByValue(Integer value) {
		if (null == value)
			return null;
		for (AccountAuditEnum _enum : AccountAuditEnum.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}

	public static AccountAuditEnum getByDesc(String description) {
		if (null == description)
			return null;
		for (AccountAuditEnum _enum : AccountAuditEnum.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
}
