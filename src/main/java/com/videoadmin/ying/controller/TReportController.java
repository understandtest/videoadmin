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
import com.videoadmin.utils.DoubleUtil;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.ying.service.TReportService;


@Controller
@RequestMapping("/admin/report")
public class TReportController extends BaseController{
	
	@Autowired
	private TReportService tReportService;
    
	@RequestMapping("/list")
	public String sysUserList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取统计记录信息list入参:",paraMap);
		Page<?> page = tReportService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取统计记录信息list出参:",records);
		for (Map<String, Object> map : records) {
			Double rechargeNumber=0.00;
			Double loginNumber=0.00;
			Double rechangePrice=0.00;
			if(map.get("rechargeNumber")!=null && !("").equals(map.get("rechargeNumber"))){
				rechargeNumber = DoubleUtil.getDoubleValueOfString(map.get("rechargeNumber").toString(), 0.00);
			}
			if(map.get("loginNumber")!=null && !("").equals(map.get("loginNumber"))){
				loginNumber = DoubleUtil.getDoubleValueOfString(map.get("loginNumber").toString(), 0.00);
			}
			if(map.get("rechangePrice")!=null && !("").equals(map.get("rechangePrice"))){
				rechangePrice = DoubleUtil.getDoubleValueOfString(map.get("rechangePrice").toString(), 0.00);
			}
			if(rechargeNumber!=0 & loginNumber!=0){
				Double rechargeAvg = DoubleUtil.divideForRoundHalfUp(rechargeNumber, loginNumber, 2);
				map.put("rechargeAvg", rechargeAvg+ "%");
			}else{
				map.put("rechargeAvg", "0.0%");
			}
			if(rechargeNumber!=0 ){
				Double ARPU = DoubleUtil.divideForRoundHalfUp(rechangePrice,rechargeNumber, 2);
				map.put("ARPU", ARPU+ "%");
			}else{
				map.put("ARPU", "0.0%");
			}
		}
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/report/list";
	}
}