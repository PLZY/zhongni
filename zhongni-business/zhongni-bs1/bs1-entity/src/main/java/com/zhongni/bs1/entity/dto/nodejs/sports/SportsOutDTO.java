package com.zhongni.bs1.entity.dto.nodejs.sports;

import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import com.zhongni.bs1.entity.dto.nodejs.league.LeagueOutDTO;
import lombok.Data;

import java.util.List;

/**
 * 
 * @TableName sports
 */
@Data
public class SportsOutDTO extends BaseOutDTO {

    /**
     * 
     */
    private String sport_id;

    /**
     * 
     */
    private String sport_name;

    private String icon;

    private List<LeagueOutDTO> leagues;

    private static final long serialVersionUID = 1L;
}