package com.zhongni.bs1.entity.dto.user;

import com.zhongni.bs1.entity.dto.base.BaseInDTO;
import com.zhongni.bs1.common.constants.BusinessConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserInDTO extends BaseInDTO
{
    // 昵称
    private String nickName;
    // 自定义头像文件
    private MultipartFile imgFile;
    // 邀请码
    private String invitationCode;
    // 系统提供的默认头像的路径
    private String imgPath;
}