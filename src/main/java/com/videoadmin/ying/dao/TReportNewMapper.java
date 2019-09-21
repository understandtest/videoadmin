package com.videoadmin.ying.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.TReportNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author haha
 * @since 2018-11-01
 */
public interface TReportNewMapper extends BaseMapper<TReportNew> {
	List<Map<String,Object>> selectListPage(Page<Map<String, Object>> page,@Param("cm") Map<String, Object> paramMap);
}