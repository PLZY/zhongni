package com.zhongni.bs1.entity.dto.goods;

import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import lombok.Data;

import java.util.List;

/**
 * 货品列表查询出参对象
 */
@Data
public class GoodsOutDTO extends BaseOutDTO {
    /**
     * 商品编码
     */
    private String goodsCode;

    /**
     * 商品名称
     */
    private String goodsTitle;

    /**
     * 商品价格，单位：元
     */
    private String goodsPrice;

    /**
     * 商品排序
     */
    private Integer goodsOrder;

    /**
     * 父级商品
     */
    private String goodsParent;

    /**
     * 子商品列表
     */
    private List<GoodsOutDTO> children;

    private static final long serialVersionUID = 1L;
}