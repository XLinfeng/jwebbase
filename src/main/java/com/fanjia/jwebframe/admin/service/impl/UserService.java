package com.fanjia.jwebframe.admin.service.impl;

import com.fanjia.jwebframe.admin.entity.Suser;
import com.fanjia.jwebframe.admin.mapper.SuserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    SuserMapper suserMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Suser user = suserMapper.loadUserByUsername(s);
        if(null == user){
            throw new UsernameNotFoundException("该账号不存在");
        }
        user.setRoles(suserMapper.getUserRolesByUid(user.getId()));
        return user;
    }
}
