package com.xzit.usercenter.service.impl;

import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.entity.Resource;
import com.xzit.common.user.entity.RoleResource;
import com.xzit.common.user.model.dto.ResourceDTO;
import com.xzit.common.user.model.dto.ResourceRoleDTO;
import com.xzit.usercenter.mapper.ResourceMapper;
import com.xzit.usercenter.mapper.ResourceRoleMapper;
import com.xzit.usercenter.service.ResourceService;
import lombok.RequiredArgsConstructor;
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
    public List<ResourceDTO> listResourceByQuery(QueryVO queryVO) {

        return resourceMapper.listResourceByQuery(queryVO);
    }

    @Override
    public List<ResourceDTO> listAdminResource(QueryVO queryVO) {
        return resourceMapper.listAdminResourceByQuery(queryVO);
    }

    @Override
    public Boolean addAdminResource(Long resourceId) {
        if(resourceRoleMapper.getByRoleIdAndResourceId(2L,resourceId)!=null){
            return false;
        }
        RoleResource roleResource=new RoleResource(null,2L,resourceId);
        return resourceRoleMapper.insert(roleResource)==1;
    }

    @Override
    public Boolean dropAdminResource(Long resourceId) {
        RoleResource roleResource=resourceRoleMapper.getByRoleIdAndResourceId(2L,resourceId);
        if(roleResource==null){
            return false;
        }
        Resource resource=resourceMapper.selectById(resourceId);
        if(resource.getIsBasic()){
            return false;
        }
        return resourceRoleMapper.deleteById(roleResource)==1;
    }

}
