package com.zhongni.bs1.common.resp.nodejs;

import com.zhongni.bs1.common.constants.BusinessConstants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 通用分页出参对象
 * 继承于公共出参对象的同时在自身新增了分页相关参数
 */
public class NodePageResponse<R extends PageInfo<T>, T> extends NodeCommonResponse<List<T>> {

    private Integer pageNum;
    private Integer pageSize;
    private Long totalCount;

    protected NodePageResponse(String code, String errMsg, R data) {
        super(code, errMsg, data.getList());
        this.pageNum = data.getPageNum();
        this.pageSize = data.getPageSize();
        this.totalCount = data.getTotal();
    }

    public static <T> NodePageResponse<PageInfo<T>, T> pageSuccess(){
        return new NodePageResponse<>("", "", new PageInfo<>(new Page<>()));
    }

    public static <R extends PageInfo<T>, T> NodePageResponse<R, T> pageSuccess(R data){
        // 此处考虑的是返回前端的格式统一，保证不是null即可，如果是null就放个空集合
        if(null == data.getList()){
            data.setList(new Page<>());
        }

        return new NodePageResponse<>(BusinessConstants.NODE_DEFAULT_SUCCESS_CODE, "", data);
    }

    public static <R extends PageInfo<T>, T> NodePageResponse<R, T> pageResp(String code, String errMsg, R data){
        if(null == data.getList()){
            data.setList(new Page<>());
        }

        return new NodePageResponse<>(code, errMsg, data);
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
