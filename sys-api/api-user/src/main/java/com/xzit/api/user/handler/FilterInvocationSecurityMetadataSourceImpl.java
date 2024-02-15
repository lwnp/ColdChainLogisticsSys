package com.xzit.api.user.handler;

import com.xzit.common.user.model.dto.ResourceRoleDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {


    private static List<ResourceRoleDTO> resourceRoleList;
    public void clearDataSource(){
        resourceRoleList=null;
    }
    @PostConstruct
    public void loadDataSource(){
        resourceRoleList=null;

    }
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
