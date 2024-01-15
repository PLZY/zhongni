package com.zhongni.bs1.controller.goods;

import com.zhongni.bs1.common.resp.CommonResponse;
import com.zhongni.bs1.entity.dto.goods.GoodsOutDTO;
import com.zhongni.bs1.service.service.local.goods.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/list")
    public CommonResponse<List<GoodsOutDTO>> listGoods(){
        List<GoodsOutDTO> goods = goodsService.listGoods();
        return CommonResponse.success(goods);
    }
    @GetMapping("/list1")
    public CommonResponse<List<GoodsOutDTO>> listGoods1(){
        List<GoodsOutDTO> goods = goodsService.listGoods();
        return CommonResponse.success(goods);
    }
}
