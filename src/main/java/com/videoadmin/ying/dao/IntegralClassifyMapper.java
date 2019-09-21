package com.videoadmin.ying.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.IntegralClassify;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author hong
 * @Date 19-8-26
 */
public interface IntegralClassifyMapper extends BaseMapper<IntegralClassify> {
    List<Map<String,Object>> selectListPage(Page<Map<String, Object>> page, @Param("cm") Map<String, Object> paramMap);
}
