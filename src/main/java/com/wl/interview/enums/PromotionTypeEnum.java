package com.wl.interview.enums;

/**
 * 促销类型枚举类
 * @author wanglei
 * @date 2022/09/01
 */
public enum PromotionTypeEnum {

    /**
     * 折扣
     */
    DISCOUNT("1", "Discount"),

    /**
     * 满减
     */
    FULL_REDUCTION("2", "FullReduction");

    private String code;
    private String value;

    PromotionTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
