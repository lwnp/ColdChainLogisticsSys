package com.xzit.usercenter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.entity.RoleResource;
import com.xzit.common.user.entity.UserInfo;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.usercenter.mapper.ResourceRoleMapper;
import com.xzit.usercenter.mapper.UserInfoMapper;
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
        Long[] ids=new Long[]{4L, 5L, 6L, 8L};
        for (Long id:ids){
            RoleResource roleResource=new RoleResource(null,2L,id);
            resourceRoleMapper.insert(roleResource);
        }
    }

}
