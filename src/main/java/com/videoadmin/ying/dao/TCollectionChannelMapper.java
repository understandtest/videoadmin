package com.videoadmin.ying.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.TCollectionChannel;

public interface TCollectionChannelMapper extends BaseMapper<TCollectionChannel> {
public List<Map<String,Object>> selectListPage(Page<Map<String, Object>> page,@Param("cm") Map<String, Object> paramMap);
	
	public Integer validateName(Map<String, Object> paraMap);
}