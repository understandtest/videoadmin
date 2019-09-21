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
import com.videoadmin.ying.service.TTouristService;


@Controller
@RequestMapping("/admin/tourist")
public class TTouristController extends BaseController{
	
	@Autowired
	private TTouristService tTouristService;

	
	 @RequestMapping(value = "/toEdit")
	    public Object toUpdate(ModelMap modelMap) {
		 Map<String, Object> param=new HashMap<>();
		 logger.info("{}查询游客访问权限管理列表 start...{}", param);
		 List<Map<String, Object>> list=tTouristService.selectListPage(param);
		 logger.info("{}查询游客访问权限管理列表 end...{}", list);
    	if (list!=null) {
    		Map<String, Object> tourist=list.get(0);
    		modelMap.addAttribute("tourist",tourist );	
    	    modelMap.addAttribute("types", "编辑");
        }else{
        	 modelMap.addAttribute("types", "添加");
        }
    	return "/systemSetting/tourist/edit";    
				
	    }
	 
	 	@RequestMapping(value = "/addOrUpdata")
		@ResponseBody
		public Object addOrUpdata(ModelMap modelMap,@RequestParam Map<String, Object> param) {
	 		tTouristService.addOrUpdata(param);
	
			return setSuccessResponse(modelMap);
		}
 
	
}