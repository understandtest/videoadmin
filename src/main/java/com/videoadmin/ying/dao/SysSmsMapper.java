package com.videoadmin.ying.dao;

import java.util.Map;

import com.videoadmin.ying.po.SysSms;
import com.videoadmin.base.BaseMapper;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author haha
 * @since 2019-03-24
 */
public interface SysSmsMapper extends BaseMapper<SysSms> {

	public Map<String,Object> selectSms();
}