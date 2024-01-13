package com.zhongni.bs1.entity.dto.nodejs.league;

import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import com.zhongni.bs1.entity.dto.nodejs.events.EventsOutDTO;
import lombok.Data;

import java.util.List;

/**
 * 
 * @TableName league
 */
@Data
public class LeagueOutDTO extends BaseOutDTO {

    private String league_id;

    private String league_name;

    private List<EventsOutDTO> events;

    private static final long serialVersionUID = 1L;
}