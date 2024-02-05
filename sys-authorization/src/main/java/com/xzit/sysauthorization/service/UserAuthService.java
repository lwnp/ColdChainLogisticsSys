package com.xzit.sysauthorization.service;

import com.xzit.api.user.feign.UserFeignClient;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.user.model.dto.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {
    @Autowired
    UserFeignClient userFeignClient;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServerResponse<UserDetailsDTO> res=userFeignClient.getUserDetailsByUsername(username);
        UserDetailsDTO userDetailsDTO=res.getData();
        if(userDetailsDTO==null){
            throw  new UsernameNotFoundException("用户名不存在");
        }
        return userDetailsDTO;
    }
}
