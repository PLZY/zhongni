package com.zhongni.bs1.entity.db.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据库DO对象如果需要返回数据到前端需要继承此类，并实现对应的转页面传输对象DTO的方法
 * @param <T>
 */
@Data
public abstract class BaseDO<T> implements Serializable {
    public abstract T toDTO();
}
