package com.wl.interview.domain;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订单信息
 * @author wanglei
 * @date 2022/09/01
 */
@Data
public class OrderInfo {
    /**
     * 主键
     */
    private Long orderInfoId;

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 水果编号
     */
    private Long fruitId;

    /**
     * 购买数量（斤）
     */
    private Integer purchaseQuantity;

    /**
     * 价格（购买时）
     */
    private BigDecimal realTimePrice;

    private Timestamp createDate;
    private Timestamp modifiyDate;
    private String creator;
    private String modifier;
}
