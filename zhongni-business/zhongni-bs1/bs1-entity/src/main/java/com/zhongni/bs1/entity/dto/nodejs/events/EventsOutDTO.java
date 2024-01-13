package com.zhongni.bs1.entity.dto.nodejs.events;

import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import com.zhongni.bs1.entity.dto.nodejs.bets.BetsOutDTO;
import com.zhongni.bs1.entity.dto.nodejs.markets.MarketsOutDTO;
import lombok.Data;

import java.util.List;

/**
 * 
 */
@Data
public class EventsOutDTO extends BaseOutDTO {
    /**
     *
     */
    private String match_id;

    /**
     *
     */
    private String home;

    /**
     *
     */
    private String away;

    /**
     *
     */
    private String start_date;

    /**
     *
     */
    private String time;

    /**
     *
     */
    private Integer status;

    private String home_score;

    private String away_score;

    private List<BetsOutDTO> bets;

    private MarketsOutDTO markets;

    private static final long serialVersionUID = 1L;
}