package com.xzit.usercenter.service;

import java.util.List;

public interface RoleService {
    String getRoleNameById(Long id);
    List<String> listUserRoles(String username);
}
