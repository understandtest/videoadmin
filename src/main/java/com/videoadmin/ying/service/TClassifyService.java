package com.videoadmin.ying.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TClassifyMapper;
import com.videoadmin.ying.po.TClassify;

/**
 * <p>
 * 分类  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tClassifyService")
public class TClassifyService extends BaseService<TClassify> {
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("分类查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("分类查询，入参："+page);
        page.setRecords(((TClassifyMapper)mapper).selectListPage(page, paramMap));
        logger.info("分类查询，出参："+page.toString());
        return page;
	}

	public Integer validateName(Map<String,Object> paraMap){
		return ((TClassifyMapper)mapper).validateName(paraMap);
	}
}