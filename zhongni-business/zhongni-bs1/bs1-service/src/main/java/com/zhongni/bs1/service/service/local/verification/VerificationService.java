package com.zhongni.bs1.service.service.local.verification;

import com.zhongni.bs1.entity.dto.user.VerificationInDTO;

public interface VerificationService {
    void verification(VerificationInDTO verificationInDTO);
    void checkVerificationCode(String cacheKey, String inputVerificationCode);
}
