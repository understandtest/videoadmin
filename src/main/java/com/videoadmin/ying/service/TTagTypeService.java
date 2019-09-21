package com.videoadmin.ying.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TTagTypeMapper;
import com.videoadmin.ying.po.TTagType;

/**
 * <p>
 * 标签类型类型  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tTagTypeService")
public class TTagTypeService extends BaseService<TTagType> {
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("标签类型查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("标签类型查询，入参："+page);
        page.setRecords(((TTagTypeMapper)mapper).selectListPage(page, paramMap));
        logger.info("标签类型查询，出参："+page.toString());
        return page;
	}

	public Integer validateName(Map<String,Object> paraMap){
		return ((TTagTypeMapper)mapper).validateName(paraMap);
	}

    public List<TTagType> findAll() {
	    return ((TTagTypeMapper)mapper).findAll();
    }
}