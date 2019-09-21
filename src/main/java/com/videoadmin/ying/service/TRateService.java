package com.videoadmin.ying.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TRateMapper;
import com.videoadmin.ying.po.TRate;

/**
 * <p>
 * 汇率设置  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-11-28
 */
@Service("tRateService")
public class TRateService extends BaseService<TRate> {
	
	
	public Map<String,Object> selectRateInfo()
	{
		return ((TRateMapper)mapper).selectRateInfo();
	}
}