package com.videoadmin.ying.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.utils.PropertiesUtil;
import com.videoadmin.ying.po.ScheduleJob;
import com.videoadmin.ying.po.TStar;
import com.videoadmin.ying.po.TVideoTags;
import com.videoadmin.ying.service.ScheduleJobService;

@Controller
@RequestMapping("/admin/schedulejob")
public class ScheduleJobController extends BaseController{

	@Autowired
	private ScheduleJobService scheduleJobService;
	
	@RequestMapping("/list")
	public String list(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap)
	{
		logger.info("{}获取定时任务list入参:",paraMap);
		Page<?> page = scheduleJobService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取定时任务list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/systemSetting/schedule/schedule_list";
	}
	
	@RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        if(DataUtil.isNotEmpty(param)){
        	if (param.get("id")!=null && !("").equals(param.get("id").toString())) {
        	    logger.info("{}获取定时任务信息 start...{}", param);
        	    ScheduleJob scheduleJob=new ScheduleJob();
        	    scheduleJob.setId(Integer.valueOf(param.get("id").toString()));
        	    scheduleJob=scheduleJobService.selectOne(scheduleJob);
        	    logger.info("{}获取定时任务信息end...{}", scheduleJob);
        	    modelMap.addAttribute("scheduleJob", scheduleJob);
            }
        }
        return "/systemSetting/schedule/edit";    
			
    }
	
	/**
	 * 更新定时时间
	 * @param modelMap
	 * @param scheduleJob
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Object update(ModelMap modelMap, @ModelAttribute ScheduleJob scheduleJob)
	{
		scheduleJobService.updateInfo(scheduleJob);
		return setSuccessResponse(modelMap); 
	}
	
	@RequestMapping("/satrtJob")
	@ResponseBody
	public Object satrtJob(ModelMap modelMap, @RequestParam(required = false) Map<String,Object> paraMap)
	{
		if(paraMap == null || "".equals(paraMap))
		{
			modelMap.put("retcode", "-1");
			modelMap.put("retMsg", "参数异常");
		}
		if(paraMap.get("id") == null || "".equals(paraMap.get("id")))
		{
			modelMap.put("retcode", "-1");
			modelMap.put("retMsg", "参数异常");
		}
		scheduleJobService.resumeOnly(Integer.valueOf(paraMap.get("id").toString()));
		modelMap.put("retcode", "1");
		modelMap.put("retMsg", "启动成功");
		return setSuccessResponse(modelMap); 
	}
	
	
	@RequestMapping("/pasuseJob")
	@ResponseBody
	public Object pasuseJob(ModelMap modelMap, @RequestParam(required = false) Map<String,Object> paraMap)
	{
		if(paraMap == null || "".equals(paraMap))
		{
			modelMap.put("retcode", "-1");
			modelMap.put("retMsg", "参数异常");
		}
		if(paraMap.get("id") == null || "".equals(paraMap.get("id")))
		{
			modelMap.put("retcode", "-1");
			modelMap.put("retMsg", "参数异常");
		}
		scheduleJobService.pauseOnly(Integer.valueOf(paraMap.get("id").toString()));
		modelMap.put("retcode", "1");
		modelMap.put("retMsg", "暂停成功");
		return setSuccessResponse(modelMap); 
	}
}
