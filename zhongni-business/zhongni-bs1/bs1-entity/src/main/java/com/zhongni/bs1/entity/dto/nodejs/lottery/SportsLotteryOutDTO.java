package com.zhongni.bs1.entity.dto.nodejs.lottery;

import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import lombok.Data;

import java.util.List;


/**
 *
 * @TableName sports
 */
@Data
public class SportsLotteryOutDTO extends BaseOutDTO {

    private Long lottery_code;

    private List<Long> success;

    private List<Long> fail;

    public Long getLottery_code() {
        return lottery_code;
    }

    public List<Long> getSuccess() {
        return success;
    }

    public void setSuccess(List<Long> success) {
        this.success = success;
    }

    public List<Long> getFail() {
        return fail;
    }

    public void setFail(List<Long> fail) {
        this.fail = fail;
    }

    public void setLottery_code(Long lottery_code) {
        this.lottery_code = lottery_code;
    }

    private static final long serialVersionUID = 1L;
}
