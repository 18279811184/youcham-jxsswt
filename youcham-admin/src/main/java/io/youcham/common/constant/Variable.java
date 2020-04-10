package io.youcham.common.constant;

/**
 * session
 */
public enum Variable {
	/**
	 *用户中心token
	 */
	USER_CENTER_TOKEN("user_center_token","用户中心token"),
	/**
	 * 登录用户
	 */
	LOGIN_USER("login_user","登录用户"),
	/**
	 * 登录用户部门
	 */
	LOGIN_USER_DEPART("login_user_depart","登录用户部门"),
	/**
	 * 登录用户wxuserCode
	 */
	LOGIN_WXUSER_CODE("login_wxuser_code","登录用户wxuserCode");

	private final String value;
	
	private final String description;

	private Variable(String value,String description) {
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static Variable getDescByValue(String value) {
		if (null == value)
			return null;
		for (Variable _enum : Variable.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static Variable getOrganType(String description) {
		if (null == description)
			return null;
		for (Variable _enum : Variable.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
	
}