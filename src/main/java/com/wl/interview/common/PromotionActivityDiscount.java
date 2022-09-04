package com.wl.interview.common;

import com.wl.interview.domain.Order;
import com.wl.interview.domain.OrderInfo;
import com.wl.interview.domain.PromotionActivity;

import com.wl.interview.enums.PromotionTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 促销活动(折扣类)
 * @author wanglei
 * @date 2022/09/01
 */
@Slf4j
@Component
public class PromotionActivityDiscount extends PromotionActivityAbstract{

    /**
     * 金额计算
     * @param orderInfoList 订单详情List
     * @return 订单
     */
    @Override
    public Order calculationAmount(Order order, List<OrderInfo> orderInfoList) {
        try {
            log.info("start calculating the discount");
            // 获取促销活动表 折扣数据（当前生效的）
            // 测试没有折扣情况 （A和B的情况）
            List<PromotionActivity> activityList = new ArrayList<>();
            // 测试有折扣情况（C和D的情况）
//            List<PromotionActivity> activityList = getDatabaseValue();
            // 优惠金额
            BigDecimal discountAmount = order.getDiscountAmount();
            // 优惠类型id
            String promotionList = order.getPromotionList();
            // 循环数据订单详情数据
            for (OrderInfo info : orderInfoList) {
                // 循环折扣数据对比
                for (PromotionActivity activity : activityList) {
                    // 有符合的计算优惠金额( 优惠金额 = 优惠金额 + 水果金额 * 折扣 * 斤数)
                    if (info.getFruitId().equals(activity.getFruitId())) {
                        discountAmount = discountAmount.add(
                                info.getRealTimePrice().multiply(
                                        BigDecimal.ONE.subtract(new BigDecimal(activity.getDiscount()))
                                ).multiply(BigDecimal.valueOf(info.getPurchaseQuantity())));
                        promotionList = promotionList == null ? activity.getPromotionType() : promotionList +","+ activity.getPromotionType();
                    }
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
            log.error("calculating the discount error, result : {}", e.getMessage());
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
        activity.setPromotionId(20001L);
        activity.setFruitId(10002L);
        activity.setDiscount("0.8");
        activity.setPromotionType(PromotionTypeEnum.DISCOUNT.getValue());
        activityList.add(activity);
        return activityList;
    }
}
