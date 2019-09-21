package com.videoadmin.ying.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TSuggestionMapper;
import com.videoadmin.ying.po.TSuggestion;

/**
 * <p>
 * 意见反馈  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tSuggestionService")
public class TSuggestionService extends BaseService<TSuggestion> {
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("用户意见查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("用户意见查询，入参："+page);
        page.setRecords(((TSuggestionMapper)mapper).selectListPage(page, paramMap));
        logger.info("用户意见查询，出参："+page.toString());
        return page;
	}
}