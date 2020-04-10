package io.youcham.common.constant;

/**
 * 短信模板枚举
 *
 * @author XiaWenFeng
 * @email 2410824038@qq.com
 * @description
 * @date 2019-10-25 16:42
 */
public enum NodeCodeEnum {
    /**
     * 调试测试
     */
    TSCS("SMS_176375354", "您正在申请手机注册，验证码为：${code}，5分钟内有效！");

    private final String value;

    private final String description;

    private NodeCodeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }


    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static NodeCodeEnum getDescByValue(String value) {
        if (null == value)
            return null;
        for (NodeCodeEnum _enum : NodeCodeEnum.values()) {
            if (value.equals(_enum.getValue()))
                return _enum;
        }
        return null;
    }

    public static NodeCodeEnum getOrganType(String description) {
        if (null == description)
            return null;
        for (NodeCodeEnum _enum : NodeCodeEnum.values()) {
            if (description.equals(_enum.getDescription()))
                return _enum;
        }
        return null;
    }

}
