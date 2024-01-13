package com.zhongni.bs1.entity.other;

import lombok.Data;

import java.io.File;

@Data
public class DealImgUploadFileResultObj {
    // 文件上传的路径
    private String uploadFilePath;
    // 旧文件对象
    private File oldImgFile;
    // 备份文件对象
    private File copyImgFile;

    public DealImgUploadFileResultObj(String uploadFilePath, File oldImgFile, File copyImgFile) {
        this.uploadFilePath = uploadFilePath;
        this.oldImgFile = oldImgFile;
        this.copyImgFile = copyImgFile;
    }

    public DealImgUploadFileResultObj(){}

}
