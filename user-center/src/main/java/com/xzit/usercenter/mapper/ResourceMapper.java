package com.xzit.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.entity.Resource;
import com.xzit.common.user.model.dto.ResourceDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {
    List<ResourceDTO> listResourceByQuery(@Param("query") QueryVO query);
    List<ResourceDTO> listAdminResourceByQuery(@Param("query") QueryVO query);
}
