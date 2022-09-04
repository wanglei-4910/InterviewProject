package com.wl.interview.common;


import com.wl.interview.domain.Order;
import com.wl.interview.domain.OrderInfo;
import com.wl.interview.domain.PromotionActivity;

import com.wl.interview.enums.PromotionTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 促销活动(满减类)
 * @author wanglei
 * @date 2022/09/01
 */
@Slf4j
@Component
public class PromotionActivityFullReduction extends PromotionActivityAbstract {

    /**
     * 金额计算
     * @param order 订单
     * @param orderInfoList 订单信息List
     * @return 订单
     */
    @Override
    public Order calculationAmount(Order order, List<OrderInfo> orderInfoList) {
        try {
            log.info("start calculating the fullReduction");
            // 获取促销活动表 满减数据（当前生效的）
            // 测试没有折扣情况 （A和B的情况）
            List<PromotionActivity> activityList = new ArrayList<>();
            // 测试有折扣情况（C和D的情况）
//            List<PromotionActivity> activityList = getDatabaseValue();

            // 如果不为空，满减数据排序（由大到小）
            activityList = activityList.stream()
                    .sorted(Comparator.comparing(PromotionActivity::getMoney).reversed())
                    .collect(Collectors.toList());
            // 优惠金额
            BigDecimal discountAmount = order.getDiscountAmount();
            // 优惠类型id
            String promotionList = order.getPromotionList();
            // 比较应付金额是否>=满减金额值，满足减去对应优惠金额（跳出循环）
            for(PromotionActivity activity : activityList) {
                // 应付金额 >= 满足满减金额 应付金额-满减金额，跳出循环
                if(!(order.getPayableAmount().compareTo(activity.getMoney()) < 0)) {
                    discountAmount = discountAmount.add(activity.getMoneyOff());
                    promotionList = promotionList == null ? activity.getPromotionType() : promotionList +","+ activity.getPromotionType();
                    break;
                }
            }
            log.info("Order discount calculation completed, order : {}, discountAmount : {}",
                    order.getOrderId(), discountAmount);
            // 赋值订单数据（优惠金额，应付金额）
            order.setPromotionList(promotionList);
            order.setDiscountAmount(discountAmount);
            order.setPayableAmount(order.getTotalAmount().subtract(discountAmount));
            return order;
        } catch (Exception e) {
            log.error("calculating the fullReduction error, result : {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * 未连接数据库，假定从数据库取值方法，方便测试类模拟
     * @return
     */
    public List getDatabaseValue() {
        List<PromotionActivity> activityList = new ArrayList<>();
        PromotionActivity activity = new PromotionActivity();
        activity.setPromotionId(20002L);
        activity.setMoney(new BigDecimal("100"));
        activity.setMoneyOff(new BigDecimal("10"));
        activity.setPromotionType(PromotionTypeEnum.FULL_REDUCTION.getValue());
        activityList.add(activity);
        return activityList;
    }
}
