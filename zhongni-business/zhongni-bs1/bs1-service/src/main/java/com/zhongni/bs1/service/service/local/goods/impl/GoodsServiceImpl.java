package com.zhongni.bs1.service.service.local.goods.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongni.bs1.common.constants.BusinessConstants;
import com.zhongni.bs1.service.service.local.goods.GoodsService;
import com.zhongni.bs1.entity.db.goods.Goods;
import com.zhongni.bs1.entity.dto.goods.GoodsOutDTO;
import com.zhongni.bs1.mapper.goods.GoodsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @description 针对表【squid_goods(货物信息表)】的数据库操作Service实现
* @createDate 2022-12-12 20:14:20
*/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
    implements GoodsService {

    @Override
    public List<GoodsOutDTO> listGoods() {
        //设置分页的条件，从第2的开始查询5条记录
        //Page<Goods> page = new Page<>(2,3);
        //Page<Goods> goodsPage = page(page, new LambdaQueryWrapper<Goods>().eq(Goods::getGoodsStatus, BusinessConstants.ENABLE));
        List<Goods> goodsList = list(new LambdaQueryWrapper<Goods>().eq(Goods::getGoodsStatus, BusinessConstants.ENABLE));
        if(CollectionUtils.isEmpty(goodsList)){
            return new ArrayList<>();
        }
        Map<String, GoodsOutDTO> goodsDTORelationMap = new HashMap<>(goodsList.size());
        List<GoodsOutDTO> goodsDTOList = new ArrayList<>();
        List<GoodsOutDTO> childrenGoodsDTOList = new ArrayList<>();
        for (Goods goods : goodsList)
        {
            GoodsOutDTO goodsDTO = goods.toDTO();
            goodsDTORelationMap.put(goods.getGoodsCode(), goodsDTO);
            if(StringUtils.isBlank(goodsDTO.getGoodsParent())){
                goodsDTOList.add(goodsDTO);
                continue;
            }

            childrenGoodsDTOList.add(goodsDTO);
        }

        childrenGoodsDTOList.forEach(item -> {
            GoodsOutDTO parentGoodsDTO = goodsDTORelationMap.get(item.getGoodsParent());
            if(null != parentGoodsDTO){
                List<GoodsOutDTO> children = parentGoodsDTO.getChildren();
                if(null == children){
                    children = new ArrayList<>();
                    parentGoodsDTO.setChildren(children);
                }
                children.add(item);
            }
        });

        return goodsDTOList;
    }
}




