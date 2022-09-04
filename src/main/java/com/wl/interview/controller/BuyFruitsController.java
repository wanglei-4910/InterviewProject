package com.wl.interview.controller;

import com.wl.interview.common.PromotionActivityDiscount;
import com.wl.interview.common.PromotionActivityFullReduction;
import com.wl.interview.domain.Order;
import com.wl.interview.domain.OrderInfo;

import com.wl.interview.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购买水果Controller
 * @author wanglei
 * @date 2022/09/01
 */
@Slf4j
@RestController
@RequestMapping("/buyfruits")
public class BuyFruitsController {
    @Resource
    private PromotionActivityDiscount promotionActivityDiscount;
    @Resource
    private PromotionActivityFullReduction promotionActivityFullReduction;

    /**
     * 订单生成
     * @param orderInfoList 订单信息list
     * @return
     */
    @PostMapping("/createOrder")
    public Order createOrder(@RequestBody List<OrderInfo> orderInfoList) {
        try {
            log.info("start order creation");
            // 入参校验
            // 生成订单
            Order order = new Order();
            SnowFlake snowFlake = new SnowFlake(11, 11);
            order.setOrderId(snowFlake.nextId());
            BigDecimal totalAmount = BigDecimal.ZERO;
            // 计算总金额
            for (OrderInfo info : orderInfoList) {
                totalAmount = totalAmount.add(info.getRealTimePrice().multiply(BigDecimal.valueOf(info.getPurchaseQuantity())));
            }
            // 总金额
            order.setTotalAmount(totalAmount);
            // 优惠金额
            order.setDiscountAmount(BigDecimal.ZERO);
            // 应付金额
            order.setPayableAmount(totalAmount);
            // 折扣优惠
            promotionActivityDiscount.calculationAmount(order, orderInfoList);
            // 满减优惠
            promotionActivityFullReduction.calculationAmount(order, orderInfoList);
            // 订单入库
            // 订单信息入库
            return order;
        } catch (Exception e) {
            log.error("order creation error, result : {}", e.getMessage());
            throw new RuntimeException();
        }
    }
}
