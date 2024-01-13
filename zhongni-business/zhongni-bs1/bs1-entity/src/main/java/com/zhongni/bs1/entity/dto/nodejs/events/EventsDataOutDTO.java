package com.zhongni.bs1.entity.dto.nodejs.events;

import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import lombok.Data;

/**
 * 
 */
@Data
public class EventsDataOutDTO extends BaseOutDTO {
    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 类型图标
     */
    private String dataTypeIcon;

    /**
     * 主队数据
     */
    private String homeTeamData;

    /**
     * 客队数据
     */
    private String awayTeamData;

    /**
     * CSS样式风格，目前暂时由后端返回
     */
    private String showStyle;

    private static final long serialVersionUID = 1L;
}