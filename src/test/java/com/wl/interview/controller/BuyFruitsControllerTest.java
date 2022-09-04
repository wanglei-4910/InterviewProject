package com.wl.interview.controller;

import com.wl.interview.common.PromotionActivityDiscount;
import com.wl.interview.common.PromotionActivityFullReduction;
import com.wl.interview.domain.Order;
import com.wl.interview.domain.OrderInfo;
import com.wl.interview.domain.PromotionActivity;
import com.wl.interview.util.SnowFlake;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*"})
@PrepareForTest({BuyFruitsController.class})
public class BuyFruitsControllerTest {
    @InjectMocks
    BuyFruitsController buyFruitsController;
    @Mock
    private PromotionActivityDiscount promotionActivityDiscount;
    @Mock
    private PromotionActivityFullReduction promotionActivityFullReduction;

    @Before
    public void setUp() {
        promotionActivityDiscount = new PromotionActivityDiscount();
        promotionActivityFullReduction = new PromotionActivityFullReduction();

        ReflectionTestUtils.setField(buyFruitsController,"promotionActivityDiscount",promotionActivityDiscount);
        ReflectionTestUtils.setField(buyFruitsController,"promotionActivityFullReduction",promotionActivityFullReduction);
    }

    /**
     * 1、有一家超市，出售苹果和草莓。其中苹果 8 元/斤，草莓 13 元/斤。
     * 现在顾客 A 在超市购买了若干斤苹果和草莓，需要计算一共多少钱？
     */
    @Test
    public void createOrderA() {
        List listMock = new ArrayList();
        listMock = fruitsCreateA(listMock);
        Order order = buyFruitsController.createOrder(listMock);
        // 8*2+13*2=42
        Assert.assertEquals(order.getPayableAmount(), new BigDecimal(42));
    }

    /**
     * 超市增加了一种水果芒果，其定价为 20 元/斤。
     * 现在顾客 B 在超市购买了若干斤苹果、 草莓和芒果，需计算一共需要多少钱？
     */
    @Test
    public void createOrderB() {
        List listMock = new ArrayList();
        listMock = fruitsCreateB(listMock);
        Order order = buyFruitsController.createOrder(listMock);
        // 8*2+13*2+20*2=82
        Assert.assertEquals(order.getPayableAmount(), new BigDecimal(82));
    }

    /**
     * --------------------------- 因为没有连接数据库，代码中模拟的数据库取值 测之前更改:
     * calculationAmount()中的activityList取值形式，不然断言金额不正确报错
     * ------------------------------------
     * 超市做促销活动，草莓限时打 8 折。
     * 现在顾客 C 在超市购买了若干斤苹果、 草莓和芒果，需计算一共需要多少钱？
     */
    @Test
    public void createOrderC() {
        List listMock = new ArrayList();
        listMock = fruitsCreateB(listMock);
        Order order = buyFruitsController.createOrder(listMock);
        // 8*2+13*0.8*2+20*2=76.8
        BigDecimal pay = new BigDecimal("76.8");
        Assert.assertEquals(order.getPayableAmount(), pay);
    }

    /**
     * --------------------------- 因为没有连接数据库，代码中模拟的数据库取值 测之前更改:
     * calculationAmount()中的activityList取值形式，不然断言金额不正确报错
     * ------------------------------------
     * 促销活动效果明显，超市继续加大促销力度，购物满 100 减 10 块。
     * 现在顾客 D 在超市购买了若干斤苹果、 草莓和芒果，需计算一共需要多少钱？
     */
    @Test
    public void createOrderD() {
        List listMock = new ArrayList();
        listMock = fruitsCreateD(listMock);
        Order order = buyFruitsController.createOrder(listMock);
        // 8*2+13*0.8*2+20*5=136.8-10=126.8
        BigDecimal pay = new BigDecimal("126.8");
        Assert.assertEquals(order.getPayableAmount(), pay);
    }

    /**
     * A购买水果数据生成
     * 购买水果数据(10001苹果，10002草莓，10003芒果)
     */
    public List fruitsCreateA(List listMock) {
        SnowFlake snowFlake = new SnowFlake(11, 11);
        OrderInfo info1 = new OrderInfo();
        OrderInfo info2 = new OrderInfo();
        // 苹果8元购买2斤
        info1.setFruitId(10001L);
        info1.setRealTimePrice(BigDecimal.valueOf(8));
        info1.setPurchaseQuantity(2);
        // 草莓13元购买两斤
        info2.setFruitId(10002L);
        info2.setRealTimePrice(BigDecimal.valueOf(13));
        info2.setPurchaseQuantity(2);
        listMock.add(info1);
        listMock.add(info2);
        return listMock;
    }
    /**
     * B,C购买水果数据生成
     * 购买水果数据(10001苹果，10002草莓，10003芒果)
     */
    public List fruitsCreateB(List listMock) {
        SnowFlake snowFlake = new SnowFlake(11, 11);
        OrderInfo info1 = new OrderInfo();
        OrderInfo info2 = new OrderInfo();
        OrderInfo info3 = new OrderInfo();
        // 苹果8元购买2斤
        info1.setFruitId(10001L);
        info1.setRealTimePrice(BigDecimal.valueOf(8));
        info1.setPurchaseQuantity(2);
        // 草莓13元购买两斤
        info2.setFruitId(10002L);
        info2.setRealTimePrice(BigDecimal.valueOf(13));
        info2.setPurchaseQuantity(2);
        // 芒果20元购买两斤
        info3.setFruitId(10003L);
        info3.setRealTimePrice(BigDecimal.valueOf(20));
        info3.setPurchaseQuantity(2);
        listMock.add(info1);
        listMock.add(info2);
        listMock.add(info3);
        return listMock;
    }

    /**
     * D购买水果数据生成
     * 购买水果数据(10001苹果，10002草莓，10003芒果)
     */
    public List fruitsCreateD(List listMock) {
        SnowFlake snowFlake = new SnowFlake(11, 11);
        OrderInfo info1 = new OrderInfo();
        OrderInfo info2 = new OrderInfo();
        OrderInfo info3 = new OrderInfo();
        // 苹果8元购买2斤
        info1.setFruitId(10001L);
        info1.setRealTimePrice(BigDecimal.valueOf(8));
        info1.setPurchaseQuantity(2);
        // 草莓13元购买两斤
        info2.setFruitId(10002L);
        info2.setRealTimePrice(BigDecimal.valueOf(13));
        info2.setPurchaseQuantity(2);
        // 芒果20元购买五斤
        info3.setFruitId(10003L);
        info3.setRealTimePrice(BigDecimal.valueOf(20));
        info3.setPurchaseQuantity(5);
        listMock.add(info1);
        listMock.add(info2);
        listMock.add(info3);
        return listMock;
    }

}