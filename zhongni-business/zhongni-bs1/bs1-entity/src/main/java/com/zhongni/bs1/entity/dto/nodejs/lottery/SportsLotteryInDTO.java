package com.zhongni.bs1.entity.dto.nodejs.lottery;

import com.zhongni.bs1.common.annotation.NotNull;
import com.zhongni.bs1.entity.dto.base.BaseInDTO;

import java.math.BigDecimal;
import java.util.List;

public class SportsLotteryInDTO extends BaseInDTO {

    // 商券类型
    @NotNull
    private String type;

    @NotNull
    private List<SportsBetsInDTO> bets;

    @NotNull
    private BigDecimal totalStake;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SportsBetsInDTO> getBets() {
        return bets;
    }

    public void setBets(List<SportsBetsInDTO> bets) {
        this.bets = bets;
    }

    public BigDecimal getTotalStake() {
        return totalStake;
    }

    public void setTotalStake(BigDecimal totalStake) {
        this.totalStake = totalStake;
    }

    private static final long serialVersionUID = 1L;
}