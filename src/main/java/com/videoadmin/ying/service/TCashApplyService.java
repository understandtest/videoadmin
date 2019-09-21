package com.videoadmin.ying.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TCashApplyMapper;
import com.videoadmin.ying.po.TCashApply;

/**
 * <p>
 * 提现申请表  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-11-28
 */
@Service("tCashApplyService")
public class TCashApplyService extends BaseService<TCashApply> {

	
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("提现申请，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("提现申请，入参："+page);
        page.setRecords(((TCashApplyMapper)mapper).selectListPage(page, paramMap));
        logger.info("提现申请，出参："+page.toString());
        return page;
	}
}