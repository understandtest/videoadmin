/**
 * 文件名: SysSmsController.java
 * 创建者: ying
 * 日期： 2019-3-24 下午10:53:29
 * 来源：ying
 */
package com.videoadmin.ying.controller;

import com.videoadmin.base.BaseController;
import com.videoadmin.ying.po.SysSms;
import com.videoadmin.ying.service.SysSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件名: SysSmsController.java
 * 创建者: ying
 * 日期： 2019-3-24 下午10:53:29
 * 来源：ying
 * 描述：
 */
@Controller
@RequestMapping("/admin/syssms")
public class SysSmsController extends BaseController {

	@Autowired
	private SysSmsService sysSmsService;
	
	
	@RequestMapping(value = "/toEdit")
    public Object toEdit(ModelMap modelMap) {
		 Map<String, Object> param=new HashMap<String, Object>();
		 param = sysSmsService.selectSms();
		 modelMap.put("result", param);
	 return "/systemSetting/sms/edit";    
    }
	
	@RequestMapping(value = "/toUpdate")
	@ResponseBody
    public Object toUpdate(ModelMap modelMap, SysSms sysSms) {
	 sysSmsService.updateSms(sysSms);
	 return setSuccessResponse(modelMap);   
    }
	
}
