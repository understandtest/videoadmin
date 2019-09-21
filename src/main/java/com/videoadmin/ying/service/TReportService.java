package com.videoadmin.ying.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TReportMapper;
import com.videoadmin.ying.po.TReport;

/**
 * <p>
 * 统计表
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tReportService")
public class TReportService extends BaseService<TReport> {
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("统计记录查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("统计记录查询，入参："+page);
        page.setRecords(((TReportMapper)mapper).selectListPage(page, paramMap));
        logger.info("统计记录查询，出参："+page.toString());
        return page;
	}
}