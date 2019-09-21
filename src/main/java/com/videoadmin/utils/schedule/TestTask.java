package com.videoadmin.utils.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.videoadmin.utils.redis.CacheUtil;
import com.videoadmin.ying.po.TReportNew;
import com.videoadmin.ying.service.TReportNewService;

@Component("testTask")
public class TestTask {

	protected final Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private TReportNewService tReportNewService;
	
	public void test(String params){
		logger.info("我是带参数的test方法，正在被执行，参数为：" + params);
		String date = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date dates=new Date();
		if("0".equals(params))
		{
			 date = format.format(dates);
		}
		else
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dates);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			dates = calendar.getTime();
	    	date = format.format(dates);
		}
		List<String> fromCodeList = new ArrayList<String>();
		if(CacheUtil.getCache().get("fromCode") !=null && !"".equals(CacheUtil.getCache().get("fromCode")))
    	{
			fromCodeList = (List<String>) CacheUtil.getCache().get("fromCode");
    	}
		
		if(fromCodeList != null && fromCodeList.size()>0)
		{
			//获取渠道码，然后读取缓存入库
			for(String fck:fromCodeList)
			{
				for(int i=1;1<5;i++)
				{
					String key1="tongji-"+i+"-"+date+"-"+fck;
					if(CacheUtil.getCache().get(key1) != null && !"".equals(CacheUtil.getCache().get(key1)) )
					{
						TReportNew report = new TReportNew();
						report.setActionType(i);
						report.setCurentDay(date);
						report.setFromCode(fck);
						report =tReportNewService.selectOne(report);
						if(report == null || "".equals(report))
						{
							report = new TReportNew();
							report.setActionType(i);
							report.setCurentDay(date);
							report.setFromCode(fck);
						}
						if(i ==3)
						{
							String ty = (String)CacheUtil.getCache().get(key1);
							String[] tya = ty.split(",");
							report.setRechangeNum(Integer.valueOf(tya[0]));
							report.setRechangePrice(Double.valueOf(tya[1]));
						}
						else
						{
							String num = (String) CacheUtil.getCache().get(key1);
							report.setTimesNum(Integer.valueOf(num));
						}
						if(report != null && !"".equals(report))
						{
							tReportNewService.update(report);
						}
						//CacheUtil.getCache().del(key1);
					}
				}
			}
		}
		
	}
}
