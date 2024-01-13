package com.zhongni.bs1.entity.dto.nodejs.events;

import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import com.zhongni.bs1.entity.dto.nodejs.markets.MarketsOutV2DTO;
import com.zhongni.bs1.entity.dto.nodejs.plate.PlateOutV2DTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 
 */
@Data
public class EventsOutV2DTO extends BaseOutDTO {
    private String eventId;
    private String homeTeamName;
    private String homeTeamIcon;
    private String awayTeamName;
    private String awayTeamIcon;
    private String startTime;

    /**
     * 赛事数据：角球数，进球数，红黄牌等
     */
    private List<EventsDataOutDTO> eventData;

    /**
     * 赛事对应运动类型下的所有盘口信息
     */
    private List<PlateOutV2DTO> palteInfoList;

    /**
     * 赛事对应的玩法信息
     */
    private List<MarketsOutV2DTO> marketList;

    private static final long serialVersionUID = 1L;
}