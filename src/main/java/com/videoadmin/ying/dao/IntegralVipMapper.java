package com.videoadmin.ying.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.IntegralVip;

import java.util.List;
import java.util.Map;

/**
 * @Author hong
 * @Date 19-8-26
 */
public interface IntegralVipMapper extends BaseMapper<IntegralVip> {
    List<Map<String, Object>> selectListPage(Page<Map<String, Object>> page, Map<String, Object> paramMap);
}
