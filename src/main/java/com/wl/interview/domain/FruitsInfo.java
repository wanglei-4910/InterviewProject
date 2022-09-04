package com.wl.interview.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 水果
 * @author wanglei
 * @date 2022/09/01
 */
@Data
public class FruitsInfo {
    /**
     * 主键
     */
    private Long fruitId;

    /**
     * 水果名
     */
    private String fruitName;

    /**
     * 水果价格（斤/元）
     */
    private BigDecimal fruitPrice;

    private Timestamp createDate;
    private Timestamp modifiyDate;
    private String creator;
    private String modifier;
}
