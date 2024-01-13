package com.zhongni.bs1.entity.dto.nodejs.plate;

import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import lombok.Data;

import java.util.List;

@Data
public class PlateOutDTO extends BaseOutDTO {
    private String event_id;
    private String home;
    private String home_icon;
    private String away;
    private String away_icon;
    private Long start_time;
    private List<List<String>> game_data;
    private List<List<String>> types;
    private List<List<Object>> type_data;
}
