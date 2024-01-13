package com.zhongni.bs1.entity.dto.nodejs.league;

import com.zhongni.bs1.common.annotation.NotNull;
import com.zhongni.bs1.entity.dto.base.BaseInDTO;
import lombok.Data;

import java.util.List;

/**
 * 
 */
@Data
public class LeagueInDTO extends BaseInDTO {

    @NotNull
    private List<String> expand_leagues_id;

    @NotNull
    private Integer type;

    private Integer market_id;

    private static final long serialVersionUID = 1L;
}