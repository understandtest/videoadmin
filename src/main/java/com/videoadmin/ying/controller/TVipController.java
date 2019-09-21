package com.videoadmin.ying.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.videoadmin.base.BaseController;
import com.videoadmin.ying.service.TVipService;


@Controller
@RequestMapping("/admin/vip")
public class TVipController extends BaseController{
	
	@Autowired
	private TVipService tVipService;

	
	 @RequestMapping(value = "/toEdit")
	    public Object toUpdate(ModelMap modelMap) {
		 Map<String, Object> param=new HashMap<>();
		 logger.info("{}查询会员卡管理 start...{}", param);
		 List<Map<String, Object>> list=tVipService.selectListPage(param);
		 logger.info("{}查询会员卡管理 end...{}", list);
    	if (list!=null) {
    		for (Map<String, Object> tVip : list) {
    			Integer cardtype=Integer.valueOf(tVip.get("cardType").toString());

				switch (cardtype){
					case 1: {
						modelMap.addAttribute("dayCard", tVip);
						break;
					}
					case 2: {
						modelMap.addAttribute("monthCard", tVip);
						break;
					}
					case 3: {
						modelMap.addAttribute("halfYearCard", tVip);
						break;
					}
					case 4: {
						modelMap.addAttribute("yearCard", tVip);
						break;
					}
					case 5: {
						modelMap.addAttribute("permanentCard", tVip);
						break;
					}
				}

			}
    	   
    	    modelMap.addAttribute("types", "编辑");
        }else{
        	 modelMap.addAttribute("types", "添加");
        }
    	return "fundManager/vip/edit";
				
	    }
	 
	 	@RequestMapping(value = "/addOrUpdata")
		@ResponseBody
		public Object addOrUpdata(ModelMap modelMap,@RequestParam Map<String, Object> param) {
	 		tVipService.addOrUpdata(param);
	
			return setSuccessResponse(modelMap);
		}
 
	
}