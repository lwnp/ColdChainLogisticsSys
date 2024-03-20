package com.xzit.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.model.dto.ResourceDTO;
import com.xzit.common.user.model.dto.ResourceRoleDTO;

import java.util.List;

public interface ResourceService {
    List<ResourceRoleDTO> listResourceRoles();
    IPage<ResourceDTO> listResourceByQuery(QueryVO queryVO);
}
