package com.videoadmin.ying.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TTagsMapper;
import com.videoadmin.ying.po.TTags;

/**
 * <p>
 * 标签  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tTagsService")
public class TTagsService extends BaseService<TTags> {
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("标签查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("标签查询，入参："+page);
        page.setRecords(((TTagsMapper)mapper).selectListPage(page, paramMap));
        logger.info("标签查询，出参："+page.toString());
        return page;
	}

	public Integer validateName(Map<String,Object> paraMap){
		return ((TTagsMapper)mapper).validateName(paraMap);
	}
	public Map<String, Object> getTagInfo(Map<String, Object> paramMap){
        logger.info("开始查询标签信息");
        return ((TTagsMapper)mapper).getTagInfo(paramMap);
    }

    public List<TTags> getTagListByTpId(Integer tagTypeId) {
        return ((TTagsMapper)mapper).getTagListByTpId(tagTypeId);
    }
}