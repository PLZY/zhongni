package com.zhongni.bs1.entity.db.goods;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhongni.bs1.common.util.MoneyUtil;
import com.zhongni.bs1.entity.db.base.BaseDO;
import com.zhongni.bs1.entity.dto.goods.GoodsOutDTO;
import lombok.Data;

/**
 * 货物信息表
 * @TableName squid_goods
 */
@TableName(value ="squid_goods")
@Data
public class Goods extends BaseDO<GoodsOutDTO> {
    /**
     * 商品编码
     */
    @TableId(value = "goods_code")
    private String goodsCode;

    /**
     * 商品名称
     */
    @TableField(value = "goods_title")
    private String goodsTitle;

    /**
     * 商品价格，单位：分
     */
    @TableField(value = "goods_price")
    private Long goodsPrice;

    /**
     * 商品排序
     */
    @TableField(value = "goods_order")
    private Integer goodsOrder;

    /**
     * 父级商品编码
     */
    @TableField(value = "goods_parent")
    private String goodsParent;

    /**
     * 商品状态 ENABLE-有效，DISABLE-无效
     */
    @TableField(value = "goods_status")
    private String goodsStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Goods(){}

    public Goods(GoodsOutDTO goodsDTO){
        this.setGoodsCode(goodsDTO.getGoodsCode());
        this.setGoodsTitle(goodsDTO.getGoodsTitle());
        this.setGoodsPrice(MoneyUtil.yuan2LongFen(goodsDTO.getGoodsPrice()));
        this.setGoodsOrder(goodsDTO.getGoodsOrder());
        this.setGoodsParent(goodsDTO.getGoodsParent());
    }

    @Override
    public GoodsOutDTO toDTO(){
        GoodsOutDTO goodsDTO = new GoodsOutDTO();
        goodsDTO.setGoodsCode(this.getGoodsCode());
        goodsDTO.setGoodsTitle(this.getGoodsTitle());
        goodsDTO.setGoodsPrice(MoneyUtil.longFen2Yuan(this.getGoodsPrice()));
        goodsDTO.setGoodsOrder(this.getGoodsOrder());
        goodsDTO.setGoodsParent(this.getGoodsParent());
        return goodsDTO;
    }
}