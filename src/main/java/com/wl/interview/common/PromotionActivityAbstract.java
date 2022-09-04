package com.wl.interview.common;

import com.wl.interview.domain.Order;
import com.wl.interview.domain.OrderInfo;

import java.util.List;

/**
 * 促销活动抽象类
 * @author wanglei
 * @date 2022/09/01
 */
public abstract class PromotionActivityAbstract {

    /**
     * 金额计算
     * @param order 订单
     * @param orderInfoList 订单信息List
     * @return 订单
     */
    public abstract Order calculationAmount(Order order, List<OrderInfo> orderInfoList);
}
