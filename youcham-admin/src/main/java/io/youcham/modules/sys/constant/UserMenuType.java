package io.youcham.modules.sys.constant;

import java.util.*;

/**
 * 用户菜单类型，标明用户是什么系统下的用户
 */
public enum UserMenuType {
    MENU1("@001", "利用省外资金"),
    MENU2("@002", "调度"),
    MENU3("@003", "贸易摩擦预计检测"),
    MENU4("@004", "重点建设项目库"),
    MENU5("@005", "参展绩效评估"),
    MENU6("@006", "对外投资和经济合作"),
    MENU7("@007", "招商引资重大项目监控"),
    MENU8("@008", "乡、校、战友名录"),
    MENU9("@009", "外贸预警");

    private final String value;

    private final String description;

    private UserMenuType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static UserMenuType getByValue(Integer value) {
        if (null == value)
            return null;
        for (UserMenuType _enum : UserMenuType.values()) {
            if (value.equals(_enum.getValue()))
                return _enum;
        }
        return null;
    }

    public static UserMenuType getByDesc(String description) {
        if (null == description)
            return null;
        for (UserMenuType _enum : UserMenuType.values()) {
            if (description.equals(_enum.getDescription()))
                return _enum;
        }
        return null;
    }

    /**
     * 获取所有枚举对象
     *
     * @return
     */
    public static List<Map<String, Object>> getUserMenuTypeAll() {
        List<Map<String, Object>> list = new ArrayList<>();
        UserMenuType[] us = UserMenuType.values();
        for (int i = 0; i < us.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", us[i].getValue());
            map.put("description", us[i].getDescription());
            list.add(map);
        }
        return list;
    }
}
