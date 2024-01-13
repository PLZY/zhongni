package com.zhongni.bs1.entity.dto.nodejs.markets;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import com.zhongni.bs1.entity.dto.nodejs.bets.BetsOutDTO;
import lombok.Data;

import java.util.List;

/**
 * 
 */
@Data
public class MarketsOutDTO extends BaseOutDTO {
    /**
     * 
     */
    private String market_id;

    /**
     * 
     */
    private String market_name;

    private List<BetsOutDTO> bets;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}