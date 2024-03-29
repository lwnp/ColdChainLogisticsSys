package com.xzit.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.sys.model.vo.EmailVO;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.common.user.model.vo.UserInfoVO;

public interface UserInfoService {
    UserInfoDTO loadUserInfoByContext();
    Boolean updateUserInfo(UserInfoVO userInfoVO,Long userInfoId);
    IPage<UserInfoDTO> loadUserInfo(QueryVO queryVO);
    Boolean updateEmail(EmailVO emailVO,Long userInfoId);
    Boolean forbiddenAvatar(Long userInfoId);
    Boolean forbiddenNickname(Long userInfoId);
    void sendEmailByUsername(String username);
    IPage<UserInfoDTO> listAdminInfoByQuery(QueryVO queryVO);
    UserInfoDTO getUserInfoByUserId(Long userId);
    Boolean isValidCourier(Long userInfoId);

}
