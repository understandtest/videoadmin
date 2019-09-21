package com.videoadmin.ying.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TAgentMapper;
import com.videoadmin.ying.po.TAgent;

/**
 * <p>
 * 代理表  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-11-28
 */
@Service("tAgentService")
public class TAgentService extends BaseService<TAgent> {
	
	
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("代理商查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("代理商查询，入参："+page);
        page.setRecords(((TAgentMapper)mapper).selectListPage(page, paramMap));
        logger.info("代理商查询，出参："+page.toString());
        return page;
	}
	
	public List<Map<String,Object>> selectAgentList()
	{
		return ((TAgentMapper)mapper).selectAgentList();
	}
}