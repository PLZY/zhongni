package com.zhongni.bs1.entity.dto.nodejs.events;

import com.zhongni.bs1.common.annotation.NotNull;
import com.zhongni.bs1.entity.dto.base.BaseInDTO;
import lombok.Data;

@Data
public class EventsInDTO extends BaseInDTO {
    @NotNull
    private String eventId;

    private String plateId;
}
