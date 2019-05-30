package com.fanjia.jwebframe.components;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fanjia.jwebframe.admin.entity.Menu;
import com.fanjia.jwebframe.admin.entity.Role;
import com.fanjia.jwebframe.admin.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class CustomFilterinvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    MenuMapper menuMapper;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        List<Menu> allmenus = menuMapper.getAllMenus();
        for (Menu menu:allmenus){
            if(antPathMatcher.match(menu.getPattern(),requestUrl)){
                List<Role> roles = menu.getRoles();

                List<String> roleArr = new ArrayList<String>();
                roles.forEach(item->roleArr.add(item.getRoleCode()));
               /* String[] roleArr = new String[roles.size()];
                for (int i=0;i<roleArr.length;i++){
                    roleArr[i] = roles.get(i).getRoleName();
                }*/

                return SecurityConfig.createList(roleArr.stream().toArray(String[]::new));
            }
        }

        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
