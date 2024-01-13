package com.zhongni.bs1.task.ordertask;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhongni.bs1.common.enums.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
public class OrderTask {

    @Autowired
    private ThreadPoolTaskExecutor asyncTaskThreadPool;


    @Scheduled(cron = "${schedule.ordercheck.cron}")
    public void orderSyncTask() {
        log.debug("orderSyncTask start execute...");
        // 直接使用线程池异步执行，防止阻塞导致后续的定时任务均无法执行
        asyncTaskThreadPool.execute(this::checkAndUpdateOrderStatus);
        log.debug("orderSyncTask end execute...");
    }

    private void checkAndUpdateOrderStatus()
    {
        // todo 定时任务如果日后进行了集群，此处需要加分布式锁，限制同一时间只有一个定时任务来执行此逻辑，防止数据混乱
//        List<Order> list = orderService.list(new LambdaQueryWrapper<Order>().eq(Order::getOrderStatus, OrderStatusEnum.DEALING.getCode()));
//        if(CollectionUtils.isEmpty(list))
//        {
//            log.debug("have no order info need deal, skip...");
//            return;
//        }
//
//        log.info("deal order info is {}", JSON.toJSONString(list));
//        list.forEach(item -> item.setOrderStatus(OrderStatusEnum.COMPLETE.getCode()));
//        orderService.updateBatchById(list);
    }
}
