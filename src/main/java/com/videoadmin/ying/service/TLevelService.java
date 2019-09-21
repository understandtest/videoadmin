package com.videoadmin.ying.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TLevelMapper;
import com.videoadmin.ying.po.TLevel;

/**
 * <p>
 * 等级表  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tLevelService")
public class TLevelService extends BaseService<TLevel> {
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("等级查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("等级查询，入参："+page);
        page.setRecords(((TLevelMapper)mapper).selectListPage(page, paramMap));
        logger.info("等级查询，出参："+page.toString());
        return page;
	}

	public Integer validateName(Map<String,Object> paraMap){
		return ((TLevelMapper)mapper).validateName(paraMap);
	}
}