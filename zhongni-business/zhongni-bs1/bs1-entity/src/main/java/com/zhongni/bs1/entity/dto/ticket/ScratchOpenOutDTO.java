package com.zhongni.bs1.entity.dto.ticket;

import com.zhongni.bs1.entity.dto.base.BaseInDTO;
import lombok.Data;

import java.util.List;

/**
 * 刮刮乐开启出参对象
 */
@Data
public class ScratchOpenOutDTO extends BaseInDTO {

    private List<TicketIndexOutDTO> scratchImage;

    private List<TicketIndexOutDTO> scratchBonus;
}
