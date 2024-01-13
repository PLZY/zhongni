package com.zhongni.bs1.entity.dto.nodejs.bets;

import com.zhongni.bs1.common.annotation.NotNull;
import com.zhongni.bs1.entity.dto.base.BaseInDTO;
import lombok.Data;

import java.util.List;

/**
 * 
 */
@Data
public class BetsInDTO extends BaseInDTO {

    @NotNull
    private List<String> bets;

    private static final long serialVersionUID = 1L;
}