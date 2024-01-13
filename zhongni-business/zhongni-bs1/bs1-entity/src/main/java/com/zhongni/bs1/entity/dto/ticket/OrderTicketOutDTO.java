package com.zhongni.bs1.entity.dto.ticket;

import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import lombok.Data;

/**
 */
@Data
public class OrderTicketOutDTO extends BaseOutDTO {
    /**
     * 商券号，由本系统生成的，返回给前端使用的
     */
    private String ticketCode;

    /**
     * 对应售出的订单号
     */
    private Long orderNo;

    /**
     * 商券类型，SCRATCH-刮刮乐，MINE-挖矿
     */
    private String ticketType;

    /**
     * 商券状态，UNOPEN-未开，OPENED-已开，CASHED-已兑奖，OPENING-开采中（矿工券会用到）
     */
    private String ticketStatus;

    /**
     * 该商券能够赢得的奖金，单位：元
     */
    private String winMoney;

    private static final long serialVersionUID = 1L;
}