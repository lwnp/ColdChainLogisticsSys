package com.xzit.usercenter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.sys.constant.UserConstant;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.entity.AuthUser;
import com.xzit.common.user.entity.RoleResource;
import com.xzit.common.user.entity.UserInfo;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.usercenter.mapper.ResourceRoleMapper;
import com.xzit.usercenter.mapper.UserInfoMapper;
import com.xzit.usercenter.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = UserCenterApplication.class)
class UserCenterApplicationTests {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    ResourceRoleMapper resourceRoleMapper;
    @Autowired
    UserMapper userMapper;


    @Test
    void contextLoads() {
        QueryVO queryVO=new QueryVO();
        queryVO.setPageNum(1);
        queryVO.setPageSize(5);
        queryVO.setQuery("mars");
        Page<UserInfoDTO> userInfoDTOPage=new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        System.out.println(userInfoMapper.getUserInfoByQuery(userInfoDTOPage,queryVO));

    }
    @Test
    void roleTest(){
//        long[] ids=new long[]{62,63,66,68,75,83,84,86,87,88,93};
////        Long[] ids=new Long[]{71L,74L,75L,76L,77L,80L,99L};
//        for (Long id:ids){
//            RoleResource roleResource=new RoleResource(null,3L,id);
//            resourceRoleMapper.insert(roleResource);
//        }
//        for(int i=70;i<105;i++){
//            RoleResource roleResource=new RoleResource(null,1L, (long) i);
//            resourceRoleMapper.insert(roleResource);
//        }

    }
    @Test
    void addCourier(){
        for(int i=14 ;i<=60;i++){
            UserInfo userInfo=new UserInfo();
            userInfo.setNickname("快递员"+i);
            userInfo.setAvatar(UserConstant.DEFAULT_AVATAR_URL);
            userInfo.setEmail("2300362038@qq.com");
            userInfo.setPhone("");
            userInfoMapper.insert(userInfo);
            AuthUser authUser=new AuthUser();
            authUser.setUsername("test"+i);
            authUser.setPassword(new BCryptPasswordEncoder().encode("123456"));
            authUser.setRoleId(3L);
            authUser.setUserInfoId(userInfo.getId());
            userMapper.insert(authUser);
        }
    }

}
