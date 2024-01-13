package com.zhongni.bs1.entity.dto.nodejs.bets;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import lombok.Data;

/**
 * 
 */
@Data
public class BetsOutV2DTO extends BaseOutDTO {

    /**
     *
     */
    private String betId;

    /**
     *
     */
    private String betName;

    /**
     *
     */
    private String betPrice;

    /**
     *
     */
    private String baseLine;

    /**
     *
     */
    private String line;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}