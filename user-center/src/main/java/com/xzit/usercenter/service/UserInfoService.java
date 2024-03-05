package com.xzit.usercenter.service;

import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.common.user.model.vo.UserInfoVO;

public interface UserInfoService {
    UserInfoDTO loadUserInfoByContext();
    Boolean updateUserInfo(UserInfoVO userInfoVO,Long userInfoId);

}
