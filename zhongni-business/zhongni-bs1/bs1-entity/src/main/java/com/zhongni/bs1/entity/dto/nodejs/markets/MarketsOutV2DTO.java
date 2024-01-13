package com.zhongni.bs1.entity.dto.nodejs.markets;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import com.zhongni.bs1.entity.dto.nodejs.bets.BetsOutV2DTO;
import lombok.Data;

import java.util.List;

/**
 * 
 */
@Data
public class MarketsOutV2DTO extends BaseOutDTO {
    /**
     * 
     */
    private String marketId;

    /**
     * 
     */
    private String marketName;

    private List<BetsOutV2DTO> betInfo;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}