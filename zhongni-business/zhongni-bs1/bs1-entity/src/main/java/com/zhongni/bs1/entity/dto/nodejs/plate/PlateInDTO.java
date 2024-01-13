package com.zhongni.bs1.entity.dto.nodejs.plate;

import com.zhongni.bs1.common.annotation.NotNull;
import com.zhongni.bs1.entity.dto.base.BaseInDTO;
import lombok.Data;

@Data
public class PlateInDTO extends BaseInDTO {
    @NotNull
    private String event_id;

    private String plate_id;
}
