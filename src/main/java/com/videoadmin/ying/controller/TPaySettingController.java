package com.videoadmin.ying.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.videoadmin.ying.po.TPayOpenSetting;
import com.videoadmin.ying.service.TPayOpenSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.videoadmin.base.BaseController;
import com.videoadmin.utils.PropertiesUtil;
import com.videoadmin.ying.po.TPaySetting;
import com.videoadmin.ying.service.TPaySettingService;


@Controller
@RequestMapping("/admin/paySetting")
public class TPaySettingController extends BaseController{
	
	@Autowired
	private TPaySettingService tPaySettingService;

	@Autowired
	private TPayOpenSettingService tPayOpenSettingService;

	
	 @RequestMapping(value = "/toEdit")
	    public Object toUpdate(ModelMap modelMap) {
		 Map<String, Object> param=new HashMap<>();
		 logger.info("{}查询支付管理列表 start...{}", param);
		 List<TPaySetting> list=tPaySettingService.queryPaySetting();
		 logger.info("{}查询支付管理列表 end...{}", list);
    	if (list!=null) {
    		for (TPaySetting tPaySetting : list) {
				if(tPaySetting.getPayType()==1){
					//支付宝
					 modelMap.addAttribute("Alipay", tPaySetting);	
				}else{
					//微信
					 modelMap.addAttribute("wxPay", tPaySetting);
				}
			}

			TPayOpenSetting payOpenSetting = tPayOpenSettingService.findOne();


			modelMap.addAttribute("payOpenSetting", payOpenSetting);
			modelMap.addAttribute("types", "编辑");
        }else{
        	 modelMap.addAttribute("types", "添加");
        }
    	modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
    	return "/paySetting/edit";    
				
	    }
	 
	 	@RequestMapping(value = "/addOrUpdata")
		@ResponseBody
		public Object addOrUpdata(ModelMap modelMap,@RequestParam Map<String, Object> param) {
	 		tPaySettingService.addOrUpdata(param);
	
			return setSuccessResponse(modelMap);
		}
 
	
}