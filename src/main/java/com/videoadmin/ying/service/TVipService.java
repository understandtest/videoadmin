package com.videoadmin.ying.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.utils.StringUtils;
import com.videoadmin.ying.dao.TPaySettingMapper;
import com.videoadmin.ying.dao.TVipHistoryMapper;
import com.videoadmin.ying.dao.TVipMapper;
import com.videoadmin.ying.po.TPaySetting;
import com.videoadmin.ying.po.TVip;
import com.videoadmin.base.BaseService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 会员卡信息表  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tVipService")
public class TVipService extends BaseService<TVip> {
	@Autowired 
	private TVipMapper tVipMapper;
	
	public List<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("VIP数据查询，入参："+paramMap);
	    List<Map<String, Object>> list=tVipMapper.selectListPage(paramMap);
        logger.info("VIP数据查询，出参："+list);
        return list;
	}



	@Transactional
	public void addOrUpdata(Map<String, Object> param) {
	    if (param!=null)
        {
	      //更新当前表
	     TVip tVip=new TVip();
	     TVip tVip2 =new TVip();
	     TVip tVip3 =new TVip();
	     TVip tVip4 =new TVip();
	     TVip tVip5 =new TVip();
	        if (param.get("dprice")!=null&& !("").equals(param.get("dprice").toString()))
	        {
	        	tVip.setPrice(Double.valueOf(param.get("dprice").toString()));
	        }
	        if (param.get("ddayNum")!=null&& !("").equals(param.get("ddayNum").toString()))
	        {
	        	tVip.setDayNum(Integer.valueOf(param.get("ddayNum").toString()));
	        }
			if (param.get("dcacheNum")!=null&& !("").equals(param.get("dcacheNum").toString()))
			{
				tVip.setCacheNum(Integer.valueOf(param.get("dcacheNum").toString()));
			}


	        if (param.get("mprice")!=null&& !("").equals(param.get("mprice").toString()))
	        {
	        	tVip2.setPrice(Double.valueOf(param.get("mprice").toString()));
	        }
	        if (param.get("mdayNum")!=null&& !("").equals(param.get("mdayNum").toString()))
	        {
	        	tVip2.setDayNum(Integer.valueOf(param.get("mdayNum").toString()));
	        }
			if (param.get("mcacheNum")!=null&& !("").equals(param.get("mcacheNum").toString()))
			{
				tVip2.setCacheNum(Integer.valueOf(param.get("mcacheNum").toString()));
			}

	        if (param.get("hprice")!=null&& !("").equals(param.get("hprice").toString()))
	        {
	        	tVip3.setPrice(Double.valueOf(param.get("hprice").toString()));
	        }
	        if (param.get("hdayNum")!=null&& !("").equals(param.get("hdayNum").toString()))
	        {
	        	tVip3.setDayNum(Integer.valueOf(param.get("hdayNum").toString()));
	        }
			if (param.get("hcacheNum")!=null&& !("").equals(param.get("hcacheNum").toString()))
			{
				tVip3.setCacheNum(Integer.valueOf(param.get("hcacheNum").toString()));
			}

			if (param.get("yprice")!=null&& !("").equals(param.get("yprice").toString()))
			{
				tVip4.setPrice(Double.valueOf(param.get("yprice").toString()));
			}
			if (param.get("ydayNum")!=null&& !("").equals(param.get("ydayNum").toString()))
			{
				tVip4.setDayNum(Integer.valueOf(param.get("ydayNum").toString()));
			}
			if (param.get("ycacheNum")!=null&& !("").equals(param.get("ycacheNum").toString()))
			{
				tVip4.setCacheNum(Integer.valueOf(param.get("ycacheNum").toString()));
			}

			if (param.get("pprice")!=null&& !("").equals(param.get("pprice").toString()))
			{
				tVip5.setPrice(Double.valueOf(param.get("pprice").toString()));
			}
			if (param.get("pdayNum")!=null&& !("").equals(param.get("pdayNum").toString()))
			{
				tVip5.setDayNum(Integer.valueOf(param.get("pdayNum").toString()));
			}
			if (param.get("pcacheNum")!=null&& !("").equals(param.get("pcacheNum").toString()))
			{
				tVip5.setCacheNum(Integer.valueOf(param.get("pcacheNum").toString()));
			}

			if(null != param.get("dId") && !"".equals(param.get("dId").toString())){
				//更新
	        	tVip.setId(Integer.valueOf(param.get("dId").toString()));
	        	//tVip.setUpdateBy(getCurrUser().getUserId());
	        	tVip.setUpdateTime(new Date());
	        	//更新会员卡表
	            logger.info("{}更新会员卡表 start...{}", tVip);
	            tVipMapper.updateById(tVip);
			}else{
				//新增
				tVip.setCardType(1);
				//tVip.setCreateBy(getCurrUser().getUserId());
				tVip.setCreateTime(new Date());
				tVipMapper.insert(tVip);
			}


	        if(null != param.get("mId") && !"".equals(param.get("mId").toString())){
				//更新
	        	tVip2.setId(Integer.valueOf(param.get("mId").toString()));
	        	//tVip2.setUpdateBy(getCurrUser().getUserId());
	        	tVip2.setUpdateTime(new Date());
	        	//更新会员卡表
	            logger.info("{}更新会员卡表 start...{}", tVip2);
	            tVipMapper.updateById(tVip2);
			}else{
				//新增
				tVip2.setCardType(2);
				//tVip2.setCreateBy(getCurrUser().getUserId());
				tVip2.setCreateTime(new Date());
				tVipMapper.insert(tVip2);
			}

	        if(null != param.get("hId") && !"".equals(param.get("hId").toString())){
				//更新
	        	tVip3.setId(Integer.valueOf(param.get("hId").toString()));
	        	//tVip3.setUpdateBy(getCurrUser().getUserId());
	        	tVip3.setUpdateTime(new Date());
	        	//更新会员卡表
	            logger.info("{}更新会员卡表 start...{}", tVip3);
	            tVipMapper.updateById(tVip3);
			}else{
				//新增
				tVip3.setCardType(3);
				//tVip3.setCreateBy(getCurrUser().getUserId());
				tVip3.setCreateTime(new Date());
				tVipMapper.insert(tVip3);
			}


			if(null != param.get("yId") && !"".equals(param.get("yId").toString())){
				//更新
				tVip4.setId(Integer.valueOf(param.get("yId").toString()));
				//tVip3.setUpdateBy(getCurrUser().getUserId());
				tVip4.setUpdateTime(new Date());
				//更新会员卡表
				logger.info("{}更新会员卡表 start...{}", tVip4);
				tVipMapper.updateById(tVip4);
			}else{
				//新增
				tVip4.setCardType(4);
				//tVip3.setCreateBy(getCurrUser().getUserId());
				tVip4.setCreateTime(new Date());
				tVipMapper.insert(tVip4);
			}


			if(null != param.get("pId") && !"".equals(param.get("pId").toString())){
				//更新
				tVip5.setId(Integer.valueOf(param.get("pId").toString()));
				//tVip3.setUpdateBy(getCurrUser().getUserId());
				tVip5.setUpdateTime(new Date());
				//更新会员卡表
				logger.info("{}更新会员卡表 start...{}", tVip5);
				tVipMapper.updateById(tVip5);
			}else{
				//新增
				tVip5.setCardType(5);
				//tVip3.setCreateBy(getCurrUser().getUserId());
				tVip5.setCreateTime(new Date());
				tVipMapper.insert(tVip5);
			}
        }
	}
}