package com.videoadmin.ying.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.ying.dao.TBannerMapper;
import com.videoadmin.ying.dao.TVersionMapper;
import com.videoadmin.ying.po.TBanner;
import com.videoadmin.ying.po.TVersion;
import com.videoadmin.base.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 版本控制  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tVersionService")
public class TVersionService extends BaseService<TVersion> {
	@Autowired 
	private TVersionMapper tVersionMapper;
	
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("版本查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("版本查询，入参："+page);
        page.setRecords(tVersionMapper.selectListPage(page, paramMap));
        logger.info("版本查询，出参："+page.toString());
        return page;
	}
	
	@Transactional
	public void addOrUpdata(Map<String, Object> param) {
	    if (param!=null)
        {
	      //更新当前表
	    TVersion tVersion=new TVersion();
	        if (param.get("versionType")!=null&& !("").equals(param.get("versionType").toString()))
	        {
	        	tVersion.setVersionType(Integer.valueOf(param.get("versionType").toString()));
	        }
	        if (param.get("versionCode")!=null&& !("").equals(param.get("versionCode").toString()))
	        {
	        	tVersion.setVersionCode(param.get("versionCode").toString());
	        }
	        if (param.get("isUpdate")!=null&& !("").equals(param.get("isUpdate").toString()))
	        {
	        	tVersion.setIsUpdate(Integer.valueOf(param.get("isUpdate").toString()));
	        }
	        if (param.get("versionUrl")!=null&& !("").equals(param.get("versionUrl").toString()))
	        {
	        	tVersion.setVersionUrl(param.get("versionUrl").toString());
	        }
	        if (param.get("versionContext")!=null&& !("").equals(param.get("versionContext").toString()))
	        {
	        	tVersion.setVersionContext(param.get("versionContext").toString());
	        }
	        if(null != param.get("id") && !"".equals(param.get("id").toString())){
				//更新
	        	tVersion.setId(Integer.valueOf(param.get("id").toString()));
	        	//tVersion.setUpdateBy(getCurrUser().getUserId());
	        	tVersion.setUpdateTime(new Date());
	        	//更新版本表
	            logger.info("{}更新版本表 start...{}", tVersion);
	            tVersionMapper.updateById(tVersion);
			}else{
				//新增
				//tVersion.setCreateBy(getCurrUser().getUserId());
				tVersion.setCreateTime(new Date());
				tVersionMapper.insert(tVersion);
			}
        }
	}
	
	public List<Map<String,Object>> getVersionNew(Map<String,Object> params)
	{
		return tVersionMapper.getVersionNew(params);
	}
}