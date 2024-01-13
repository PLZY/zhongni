package com.zhongni.bs1.entity.dto.ticket;

import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import lombok.Data;

/**
 * 商券索引信息出参
 */
@Data
public class TicketIndexOutDTO extends BaseOutDTO {
    /**
     * 位置坐标
     */
    private Integer openIndex;

    /**
     * 如果是BONUS类型，存储对应的奖金，单位：元
     */
    private String money;

    /**
     * 如果是IMAGE类型，应该存储图标id
     */
    private Long imageTypeId;

    private static final long serialVersionUID = 1L;
}