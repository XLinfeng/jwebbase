package com.fanjia.jwebframe.admin.mapper;

import com.fanjia.jwebframe.admin.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* <p>
*  Mapper 接口
* </p>
*
* @author xianglf
* @since 2019-05-29
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getAllMenus();
}
