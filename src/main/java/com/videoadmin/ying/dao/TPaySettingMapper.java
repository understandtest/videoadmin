package com.videoadmin.ying.dao;

import java.util.List;

import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.TPaySetting;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
public interface TPaySettingMapper extends BaseMapper<TPaySetting> {

	public List<TPaySetting> queryPaySetting();
}