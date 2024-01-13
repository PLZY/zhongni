package com.zhongni.bs1.entity.dto.ticket;

import com.zhongni.bs1.common.annotation.NotNull;
import com.zhongni.bs1.entity.dto.base.BaseInDTO;
import lombok.Data;

/**
 * 刮刮乐开启入参对象
 */
@Data
public class ScratchOpenInDTO extends BaseInDTO {

    // 商券编码
    @NotNull
    private String ticketCode;
}
