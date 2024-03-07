package com.xzit.usercenter.service;


import com.xzit.common.user.model.dto.UserDetailsDTO;
import com.xzit.common.user.model.vo.UserVO;

public interface UserService {
    UserDetailsDTO getUserByUsername(String username);
    Boolean updateUserLoginTime(Long userId);
    Boolean hasSameUser(String username);
    Boolean register(UserVO user);
    Boolean forbidden(Long userInfoId);

}
