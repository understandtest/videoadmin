package com.videoadmin.ying.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.ying.service.TSuggestionService;


@Controller
@RequestMapping("/admin/suggestion")
public class TSuggestionController extends BaseController{
	
	@Autowired
	private TSuggestionService tSuggestionService;
    
	@RequestMapping("/list")
	public String sysUserList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取用户意见信息list入参:",paraMap);
		Page<?> page = tSuggestionService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取用户意见信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/memberManager/suggestion/list";
	}
	
}