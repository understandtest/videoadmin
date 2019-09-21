package com.videoadmin.ying.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.ying.po.TTagType;
import com.videoadmin.base.BaseMapper;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
public interface TTagTypeMapper extends BaseMapper<TTagType> {
public List<Map<String,Object>> selectListPage(Page<Map<String, Object>> page,@Param("cm") Map<String, Object> paramMap);
	
	public Integer validateName(Map<String, Object> paraMap);

    List<TTagType> findAll();

}