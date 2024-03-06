package com.xzit.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.entity.UserInfo;
import com.xzit.common.user.model.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    Long insertUserInfo(@Param("userInfo") UserInfo userInfo);
    IPage<UserInfoDTO> getUserInfoByQuery(@Param("queryVO") QueryVO queryVO);
}
