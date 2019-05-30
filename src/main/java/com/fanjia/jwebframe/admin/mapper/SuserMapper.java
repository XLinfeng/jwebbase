package com.fanjia.jwebframe.admin.mapper;

import com.fanjia.jwebframe.admin.entity.Role;
import com.fanjia.jwebframe.admin.entity.Suser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xianglf
 * @since 2019-05-29
 */
@Mapper
public interface SuserMapper extends BaseMapper<Suser> {
    Suser loadUserByUsername(String username);
    List<Role> getUserRolesByUid(Integer id);
}
