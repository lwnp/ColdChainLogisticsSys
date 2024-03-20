package com.xzit.usercenter.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.model.dto.ResourceDTO;
import com.xzit.common.user.model.dto.ResourceRoleDTO;
import com.xzit.usercenter.mapper.ResourceMapper;
import com.xzit.usercenter.mapper.ResourceRoleMapper;
import com.xzit.usercenter.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRoleMapper resourceRoleMapper;
    private final ResourceMapper resourceMapper;
    @Override
    public List<ResourceRoleDTO> listResourceRoles() {
        return resourceRoleMapper.listResourceRoles();
    }

    @Override
    public IPage<ResourceDTO> listResourceByQuery(QueryVO queryVO) {
        Page<ResourceDTO> resourceDTOPage=new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return resourceMapper.listResourceByQuery(resourceDTOPage,queryVO);
    }
}
