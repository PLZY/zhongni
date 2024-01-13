package com.zhongni.bs1.service.service.local.goods;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhongni.bs1.entity.db.goods.Goods;
import com.zhongni.bs1.entity.dto.goods.GoodsOutDTO;

import java.util.List;

/**
*/
public interface GoodsService extends IService<Goods> {
    List<GoodsOutDTO> listGoods();
}
