package com.zhongni.bs1.entity.dto.nodejs.plate;

import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import lombok.Data;

@Data
public class PlateOutV2DTO extends BaseOutDTO {
    private String plateId;
    private String plateName;

    public PlateOutV2DTO(String plateId, String plateName) {
        this.plateId = plateId;
        this.plateName = plateName;
    }

    public PlateOutV2DTO() {
    }
}
