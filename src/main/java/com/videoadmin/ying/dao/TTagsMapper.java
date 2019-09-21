package com.videoadmin.ying.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.ying.po.TTags;
import com.videoadmin.base.BaseMapper;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
public interface TTagsMapper extends BaseMapper<TTags> {
	public List<Map<String, Object>> selectListPage(Page<Map<String, Object>> page, @Param("cm") Map<String, Object> paramMap);

	public Integer validateName(Map<String, Object> paraMap);

	public Map<String, Object> getTagInfo(Map<String, Object> paramMap);

	List<TTags> getTagListByTpId(@Param("tagTypeId") Integer tagTypeId);
}