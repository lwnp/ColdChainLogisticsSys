package com.xzit.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.sys.model.vo.EmailVO;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.entity.UserInfo;
import com.xzit.common.user.model.dto.UserDetailsDTO;
import com.xzit.common.user.model.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    Long insertUserInfo(@Param("userInfo") UserInfo userInfo);
    IPage<UserInfoDTO> getUserInfoByQuery(Page<UserInfoDTO> userInfoDTOPage, @Param("queryVO") QueryVO queryVO);
    IPage<UserInfoDTO> getAdminInfoByQuery(Page<UserInfoDTO> userInfoDTOPage, @Param("queryVO") QueryVO queryVO);
    String getEmailByUsername(@Param("username") String username);
    UserInfoDTO getUserInfoByUserId(@Param("userId") Long userId);
    IPage<UserInfoDTO> getCourierInfoByQuery(Page<UserInfoDTO> userInfoDTOPage, @Param("queryVO") QueryVO queryVO);

}
