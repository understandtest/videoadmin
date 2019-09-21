package com.videoadmin.ying.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.TBanner;

public interface TBannerMapper extends BaseMapper<TBanner> {
	public List<Map<String,Object>> selectListPage(Page<Map<String, Object>> page,@Param("cm") Map<String, Object> paramMap);
}