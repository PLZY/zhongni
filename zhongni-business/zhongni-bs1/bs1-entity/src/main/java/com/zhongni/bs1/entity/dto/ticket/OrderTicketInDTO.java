package com.zhongni.bs1.entity.dto.ticket;

import com.zhongni.bs1.common.annotation.NotNull;
import com.zhongni.bs1.entity.dto.base.BaseInDTO;
import lombok.Data;

/**
 * 商券订单入参对象
 */
@Data
public class OrderTicketInDTO extends BaseInDTO {
    // 商券类型
    @NotNull
    private String ticketType;

    // 购买数量
    @NotNull
    private Integer ticketNum;

    private static final long serialVersionUID = 1L;
}