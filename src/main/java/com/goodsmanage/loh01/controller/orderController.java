package com.goodsmanage.loh01.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goodsmanage.loh01.entity.Order;
import com.goodsmanage.loh01.entity.Result;
import com.goodsmanage.loh01.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
public class orderController {

    @Autowired
    private OrderService orderService;

    /**
     * GET /pageOrder
     * 前端调用示例： GET http://localhost:8090/pageOrder?page=1&pageSize=10
     * 注意：为了兼容前端现有做法，此接口返回格式为 { data: { list: ..., total: ... } }
     */
    @GetMapping("/listOrder")
    public Result list(){
        List<Order> list = orderService.list();
        log.info("success");
        return Result.success(list);
    }

    /**
     * POST /queryOrder
     * 前端调用示例： POST http://localhost:8090/queryOrder?page=1&pageSize=10
     * body(json) 可包含： orderNo, customerName, createTime ("YYYY-MM-DD,YYYY-MM-DD")
     * 返回格式与前端期待一致： { records: [...], total: n }
     */
    @PostMapping("/queryOrder")
    public Page<Order> query(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody Order order) {
        // 创建分页对象
        Page<Order> pageInfo = new Page<>(page, pageSize);
        // 创建查询条件
        LambdaQueryWrapper<Order> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(order.getCustomerName()), Order::getCustomerName, order.getCustomerName());
        lambdaQueryWrapper.like(StringUtils.isNotBlank(order.getOrderNo()), Order::getOrderNo, order.getOrderNo());

        // 创建时间范围查询
        if (StringUtils.isNotBlank(order.getCreateTime())) {
            // 假设前端传递的是时间范围字符串，格式为 "startTime,endTime"
            String[] timeRange = order.getCreateTime().split(",");
            if (timeRange.length == 2) {
                lambdaQueryWrapper.between(Order::getCreateTime, timeRange[0], timeRange[1]);
            } else {
                // 如果只有一个时间，则进行模糊查询
                lambdaQueryWrapper.like(Order::getCreateTime, order.getCreateTime());
            }
        }
        return orderService.page(pageInfo, lambdaQueryWrapper);
    }

    /**
     * POST /saveOrder
     * body: Order JSON
     * 返回 true/false 或 包装对象。前端判断：res.data (truthy -> 成功)
     */
    @PostMapping("/saveOrder")
    public boolean save(@RequestBody Order order){
        log.info("save order");
        return orderService.save(order);
    }

    /**
     * POST /modOrder
     * body: Order JSON (必须包含 id)
     * 返回 boolean
     */
    @PostMapping("/modOrder")
    public boolean mod(@RequestBody Order order){
        log.info("mod order");
        return orderService.updateById(order);
    }

    /**
     * GET /deleteOrder?id=123
     * 返回 boolean（前端不严格检查返回值，但我们返回 true/false）
     */
    @GetMapping("/deleteOrder")
    public boolean delete(Integer id) {
        log.info("delete order");
        return orderService.removeById(id);
    }
//分页功能实现
    @GetMapping("/pageOrder")
    public Result getUserList(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        Integer total = orderService.total();
        List<Order> row = orderService.row((page - 1) * pageSize, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("list", row);
        data.put("total", total);
        return Result.success(data);
    }
}
