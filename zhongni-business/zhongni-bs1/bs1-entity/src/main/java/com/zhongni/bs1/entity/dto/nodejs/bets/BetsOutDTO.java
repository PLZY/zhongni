package com.zhongni.bs1.entity.dto.nodejs.bets;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import lombok.Data;

/**
 * 
 */
@Data
public class BetsOutDTO extends BaseOutDTO {

    /**
     *
     */
    private String bet_id;

    /**
     *
     */
    private String bet_name;

    /**
     *
     */
    private String bet_price;

    /**
     *
     */
    private String base_line;

    /**
     *
     */
    private String line;

    private Integer match_status;

    private String bet_status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}