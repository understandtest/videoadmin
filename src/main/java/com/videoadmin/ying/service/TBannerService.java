package com.videoadmin.ying.service;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.utils.StringUtils;
import com.videoadmin.ying.dao.TBannerMapper;
import com.videoadmin.ying.dao.TNoticeMapper;
import com.videoadmin.ying.po.TBanner;
import com.videoadmin.ying.po.TNotice;
import com.videoadmin.base.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 轮播数据  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tBannerService")
public class TBannerService extends BaseService<TBanner> {
	@Autowired 
	private TBannerMapper tBannerMapper;
	
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("广告查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("广告查询，入参："+page);
        page.setRecords(tBannerMapper.selectListPage(page, paramMap));
        logger.info("广告查询，出参："+page.toString());
        return page;
	}
	
	@Transactional
	public void addOrUpdata(Map<String, Object> param) {
	    if (param!=null)
        {
	      //更新当前表
	    TBanner tBanner=new TBanner();
	        if (param.get("picType")!=null&& !("").equals(param.get("picType").toString()))
	        {
	        	tBanner.setPicType(Integer.valueOf(param.get("picType").toString()));
	        }
	        if (param.get("picUrl")!=null&& !("").equals(param.get("picUrl").toString()))
	        {
	        	tBanner.setPicUrl(param.get("picUrl").toString());
	        }
	        if (param.get("linkType")!=null&& !("").equals(param.get("linkType").toString()))
	        {
	        	tBanner.setLinkType(Integer.valueOf(param.get("linkType").toString()));
	        }
	        if (param.get("linkUrl")!=null&& !("").equals(param.get("linkUrl").toString()))
	        {
	        	tBanner.setLinkUrl(param.get("linkUrl").toString());
	        }
	        if (param.get("picFrom")!=null&& !("").equals(param.get("picFrom").toString()))
	        {
	        	tBanner.setPicFrom(Integer.valueOf(param.get("picFrom").toString()));
	        }
	        if (param.get("isShow")!=null&& !("").equals(param.get("isShow").toString()))
	        {
	        	tBanner.setIsShow(Integer.valueOf(param.get("isShow").toString()));
	        }
	        if (param.get("tagTypeId")!=null&& !("").equals(param.get("tagTypeId").toString()))
	        {
	        	tBanner.setTagTypeId(Integer.valueOf(param.get("tagTypeId").toString()));
	        }
	        if (param.get("tagId")!=null&& !("").equals(param.get("tagId").toString()))
	        {
	        	tBanner.setTagId(Integer.valueOf(param.get("tagId").toString()));
	        }
	        if(null != param.get("id") && !"".equals(param.get("id").toString())){
				//更新
	        	tBanner.setId(Integer.valueOf(param.get("id").toString()));
	        	//tBanner.setUpdateBy(getCurrUser().getUserId());
	        	tBanner.setUpdateTime(new Date());
	        	//更新广告表
	            logger.info("{}更新广告表 start...{}", tBanner);
	            tBannerMapper.updateById(tBanner);
			}else{
				//新增
				//tBanner.setCreateBy(getCurrUser().getUserId());
				tBanner.setCreateTime(new Date());
				tBannerMapper.insert(tBanner);
			}
        }
	}
}