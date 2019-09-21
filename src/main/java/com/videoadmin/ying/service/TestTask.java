package com.videoadmin.ying.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.ying.service.TClassifyService;


public class TestTask {

	protected final Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private TClassifyService tClassifyService;
	
	public void test(String params){
		logger.info("我是带参数的test方法，正在被执行，参数为：" + params);
		
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Map<String,Object> paraMap = new HashMap<String,Object>();
		Page<?> page = tClassifyService.selectListPage(paraMap);
		System.out.println("定时任务执行----"+page);
		
	}
}
