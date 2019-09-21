package com.videoadmin.ying.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TExtensionInfoMapper;
import com.videoadmin.ying.po.TExtensionInfo;

/**
 * <p>
 * 推广信息表  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-15
 */
@Service("tExtensionInfoService")
public class TExtensionInfoService extends BaseService<TExtensionInfo> {
	
	@Autowired
	private TExtensionInfoMapper tExtensionInfoMapper;
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("推广查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("推广查询，入参："+page);
        page.setRecords(tExtensionInfoMapper.selectListPage(page, paramMap));
        logger.info("推广查询，出参："+page.toString());
        return page;
	}
	
	@Transactional
	public void addOrUpdata(Map<String, Object> param) {
	    if (param!=null)
        {
	      //更新当前表
	    TExtensionInfo tExtensionInfo=new TExtensionInfo();
	        if (param.get("extensionUrl")!=null&& !("").equals(param.get("extensionUrl").toString()))
	        {
	        	tExtensionInfo.setExtensionUrl(param.get("extensionUrl").toString());
	        }
	        if (param.get("extensionContext")!=null&& !("").equals(param.get("extensionContext").toString()))
	        {
	        	tExtensionInfo.setExtensionContext(param.get("extensionContext").toString());
	        }
	        if (param.get("isShow")!=null&& !("").equals(param.get("isShow").toString()))
	        {
	        	int isShow=Integer.valueOf(param.get("isShow").toString());
	        	if(isShow==1){
	        		TExtensionInfo tInfo=new TExtensionInfo();
	        		tInfo.setIsShow(1);
	        		TExtensionInfo tInfo2=selectOne(tInfo);
	        		if(tInfo2!=null){
	        			tInfo2.setIsShow(0);
	        			tExtensionInfoMapper.updateById(tInfo2);
	        		}
	        	}
	        	tExtensionInfo.setIsShow(isShow);
	        }
	        
	        if(null != param.get("id") && !"".equals(param.get("id").toString())){
				//更新
	        	tExtensionInfo.setId(Integer.valueOf(param.get("id").toString()));
	        	//tExtensionInfo.setUpdateBy(getCurrUser().getUserId());
	        	tExtensionInfo.setUpdateTime(new Date());
	        	//更新推广表
	            logger.info("{}更新推广表 start...{}", tExtensionInfo);
	            tExtensionInfoMapper.updateById(tExtensionInfo);
			}else{
				//新增
				//tExtensionInfo.setCreateBy(getCurrUser().getUserId());
				tExtensionInfo.setCreateTime(new Date());
				tExtensionInfoMapper.insert(tExtensionInfo);
			}
        }
	}
}