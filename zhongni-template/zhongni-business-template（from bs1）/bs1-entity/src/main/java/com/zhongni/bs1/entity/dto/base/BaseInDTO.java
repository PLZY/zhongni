package com.zhongni.bs1.entity.dto.base;

import lombok.Getter;

import java.io.Serializable;
import java.util.Optional;

/**
 * 页面入参基类
 */
@Getter
public class BaseInDTO implements Serializable {
    // 当前登录的用户编码
    private Long userId;

    // 当前页
    private Integer pageNum;

    // 页大小
    private Integer pageSize;

    // 登录令牌
    private String token;

    public void setPageNum(Integer pageNum) {
        this.pageNum = Optional.ofNullable(pageNum).orElse(1);
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = Optional.ofNullable(pageSize).orElse(10);
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
