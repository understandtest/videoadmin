package com.videoadmin.ying.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TRechargeCodeMapper;
import com.videoadmin.ying.po.TRechargeCode;

/**
 * <p>
 * 充值码  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-11-28
 */
@Service("tRechargeCodeService")
public class TRechargeCodeService extends BaseService<TRechargeCode> {
	
	
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("充值码查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("充值码查询，入参："+page);
        page.setRecords(((TRechargeCodeMapper)mapper).selectListPage(page, paramMap));
        logger.info("充值码查询，出参："+page.toString());
        return page;
	}
	
	public List<LinkedHashMap<String,Object>> selectListExport(Map<String, Object> paramMap)
	{
		return ((TRechargeCodeMapper)mapper).selectListExport(paramMap);
	}
}