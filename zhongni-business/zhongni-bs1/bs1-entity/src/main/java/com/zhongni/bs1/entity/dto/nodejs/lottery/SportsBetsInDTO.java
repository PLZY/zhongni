package com.zhongni.bs1.entity.dto.nodejs.lottery;

import com.zhongni.bs1.entity.dto.base.BaseInDTO;
import lombok.Data;
import com.zhongni.bs1.common.annotation.NotNull;


@Data
public class SportsBetsInDTO extends BaseInDTO {
    @NotNull
    private Long id;

    @NotNull
    private Long stake;

    @NotNull
    private String odd;
}