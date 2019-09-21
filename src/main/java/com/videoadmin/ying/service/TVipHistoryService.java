package com.videoadmin.ying.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TVipHistoryMapper;
import com.videoadmin.ying.po.TVipHistory;

/**
 * <p>
 * 购买VIP记录  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tVipHistoryService")
public class TVipHistoryService extends BaseService<TVipHistory> {
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("购买VIP记录查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("购买VIP记录查询，入参："+page);
        page.setRecords(((TVipHistoryMapper)mapper).selectListPage(page, paramMap));
        logger.info("购买VIP记录查询，出参："+page.toString());
        return page;
	}
	 public Map<String, Object> getVipHistoryInfo(Map<String, Object> paramMap){
	        logger.info("开始查询购买VIP记录信息");
	        return ((TVipHistoryMapper)mapper).getVipHistoryInfo(paramMap);
	    }
}