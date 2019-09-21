package com.videoadmin.ying.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TCollectionChannelMapper;
import com.videoadmin.ying.po.TCollectionChannel;

/**
 * <p>
 * 采集渠道  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tCollectionChannelService")
public class TCollectionChannelService extends BaseService<TCollectionChannel> {
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("采集渠道查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("采集渠道查询，入参："+page);
        page.setRecords(((TCollectionChannelMapper)mapper).selectListPage(page, paramMap));
        logger.info("采集渠道查询，出参："+page.toString());
        return page;
	}

	public Integer validateName(Map<String,Object> paraMap){
		return ((TCollectionChannelMapper)mapper).validateName(paraMap);
	}
}