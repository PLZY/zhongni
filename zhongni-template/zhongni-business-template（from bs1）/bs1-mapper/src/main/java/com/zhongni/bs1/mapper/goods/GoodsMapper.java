package com.zhongni.bs1.mapper.goods;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhongni.bs1.entity.db.goods.Goods;

/**
* @Entity generator.entity.Goods
*/
public interface GoodsMapper extends BaseMapper<Goods> {
    IPage<Goods> selectPageVo(IPage<?> page, Integer state);
}




