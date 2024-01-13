package com.zhongni.bs1.entity.dto.ticket;

import com.zhongni.bs1.common.annotation.NotNull;
import com.zhongni.bs1.entity.dto.base.BaseInDTO;
import lombok.Data;

/**
 * 刮刮乐分页查询入参对象
 */
@Data
public class ScratchPageInDTO extends BaseInDTO {

    // 商券类型
    @NotNull
    private String ticketType;

    // 商券状态
    private String ticketStatus;
}
