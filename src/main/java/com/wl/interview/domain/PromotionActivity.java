package com.wl.interview.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订单信息
 * @author wanglei
 * @date 2022/09/01
 */
@Data
public class PromotionActivity {
    /**
     * 主键
     */
    private Long promotionId;

    /**
     * 促销类型（枚举）
     */
    private String promotionType;

    /**
     * 促销商品
     */
    private Long fruitId;

    /**
     * 促销折扣
     */
    private String discount;

    /**
     * 满足满减金额
     */
    private BigDecimal money;

    /**
     * 满减金额
     */
    private BigDecimal moneyOff;

    /**
     * 活动开始时间
     */
    private Timestamp startDate;

    /**
     * 活动结束时间
     */
    private Timestamp endDate;

    /**
     * 是否启用
     */
    private Timestamp enable;

    /**
     * 是否删除
     */
    private Timestamp delete;

    private Timestamp createDate;
    private Timestamp modifiyDate;
    private String creator;
    private String modifier;

}
