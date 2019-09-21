package com.videoadmin.ying.dao;

import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.ModuleSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Author hong
 * @Date 19-9-10
 */
public interface ModuleSettingMapper extends BaseMapper<ModuleSetting> {
    Map<String, Object> findOne(@Param("id") Integer id);
}
