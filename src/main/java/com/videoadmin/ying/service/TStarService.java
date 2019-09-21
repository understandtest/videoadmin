package com.videoadmin.ying.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.utils.StringUtils;
import com.videoadmin.ying.dao.TStarMapper;
import com.videoadmin.ying.po.TStar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 明星  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tStarService")
public class TStarService extends BaseService<TStar> {
	@Autowired 
	private TStarMapper tStarMapper;
	
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("明星查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("明星查询，入参："+page);
        page.setRecords(tStarMapper.selectListPage(page, paramMap));
        logger.info("明星查询，出参："+page.toString());
        return page;
	}
	public Integer validateName(Map<String,Object> paraMap){
		return tStarMapper.validateName(paraMap);
	}
	@Transactional
	public void addOrUpdata(Map<String, Object> param) {
	    if (param!=null)
        {
	      //更新当前表
	    TStar tStar=new TStar();
	        if (param.get("name")!=null&& !("").equals(param.get("name").toString()))
	        {
	        	tStar.setName(param.get("name").toString());
	        }
	        if (param.get("picType")!=null&& !("").equals(param.get("picType").toString()))
	        {
	        	tStar.setPicType(Integer.valueOf(param.get("picType").toString()));
	        }
	        if (param.get("headpic")!=null&& !("").equals(param.get("headpic").toString()))
	        {
	        	tStar.setHeadpic(param.get("headpic").toString());
	        }
	        if (param.get("heightNum")!=null&& !("").equals(param.get("heightNum").toString()))
	        {
	        	tStar.setHeightNum(param.get("heightNum").toString());
	        }
	        if (param.get("bwh")!=null&& !("").equals(param.get("bwh").toString()))
	        {
	        	tStar.setBwh(param.get("bwh").toString());
	        }
	        if (param.get("cup")!=null&& !("").equals(param.get("cup").toString()))
	        {
	        	tStar.setCup(param.get("cup").toString());
	        }
	        if (param.get("briefContext")!=null&& !("").equals(param.get("briefContext").toString()))
	        {
	        	tStar.setBriefContext(param.get("briefContext").toString());
	        }

	        if(StringUtils.isNotEmpty((String) param.get("heat"))){
				String heat = (String) param.get("heat");
				tStar.setHeat(Integer.parseInt(heat));
			}
	       
	        if(null != param.get("id") && !"".equals(param.get("id").toString())){
				//更新
	        	tStar.setId(Integer.valueOf(param.get("id").toString()));
	        	//tStar.setUpdateBy(getCurrUser().getUserId());
	        	tStar.setUpdateTime(new Date());
	        	//更新明星表
	            logger.info("{}更新明星表 start...{}", tStar);
	            tStarMapper.updateById(tStar);
			}else{
				//新增
				//tStar.setCreateBy(getCurrUser().getUserId());
				tStar.setCreateTime(new Date());
				tStarMapper.insert(tStar);
			}
        }
	}
}