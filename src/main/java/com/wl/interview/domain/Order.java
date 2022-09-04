package com.wl.interview.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订单
 * @author wanglei
 * @date 2022/09/01
 */
@Data
public class Order {
    /**
     * 主键
     */
    private Long orderId;

    /**
     * 使用优惠类型
     */
    private String promotionList;

    /**
     * 总金额（未优惠前金额）
     */
    private BigDecimal totalAmount;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 应付金额（优惠后金额）`
     */
    private BigDecimal payableAmount;

    /**
     * 付款时间
     */
    private Long payDate;

    private Timestamp createDate;
    private Timestamp modifiyDate;
    private String creator;
    private String modifier;
}
