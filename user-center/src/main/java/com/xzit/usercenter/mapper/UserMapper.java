package com.xzit.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzit.common.user.entity.AuthUser;
import com.xzit.common.user.model.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<AuthUser> {
    Integer forbiddenByUserInfoId(@Param("userInfoId") Long userInfoId);

}
