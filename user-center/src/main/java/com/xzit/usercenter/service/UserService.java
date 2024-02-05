package com.xzit.usercenter.service;


import com.xzit.common.user.model.dto.UserDetailsDTO;

public interface UserService {
    UserDetailsDTO getUserByUsername(String username);
}
