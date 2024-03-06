package com.xzit.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.common.user.model.vo.UserInfoVO;

public interface UserInfoService {
    UserInfoDTO loadUserInfoByContext();
    Boolean updateUserInfo(UserInfoVO userInfoVO,Long userInfoId);
    Page<UserInfoDTO> loadUserInfo(QueryVO queryVO);

}
