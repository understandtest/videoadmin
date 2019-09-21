package com.videoadmin.ying.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.videoadmin.base.BaseController;
import com.videoadmin.ying.po.TRate;
import com.videoadmin.ying.service.TRateService;


@Controller
@RequestMapping("/admin/rate")
public class TRateController extends BaseController{

	@Autowired
	private TRateService tRateService;
	
	@RequestMapping("/info")
	public String info(ModelMap modelMap)
	{
		Map<String,Object> tf = new HashMap<String,Object>();
		tf = tRateService.selectRateInfo();
		modelMap.put("rateInfo", tf);
		return "/systemSetting/rate/info";
	}
	
	
	@RequestMapping("/update")
	@ResponseBody
	public Object update(ModelMap modelMap, @RequestBody TRate tRate)
	{
		tRateService.update(tRate);
		return setSuccessResponse(modelMap);
	}
	
	
	
	
	
}
