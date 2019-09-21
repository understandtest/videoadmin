package com.videoadmin.ying.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.ying.po.TExtensionInfo;
import com.videoadmin.ying.service.TExtensionInfoService;


@Controller
@RequestMapping("/admin/extensionInfo")
public class TExtensionInfoController extends BaseController{
	
	@Autowired
	private TExtensionInfoService tExtensionInfoService;
	
	@RequestMapping("/list")
	public String sysUserList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取推广信息list入参:",paraMap);
		Page<?> page = tExtensionInfoService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取推广信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/systemSetting/extensioninfo/list";
	}
	@RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        if(DataUtil.isNotEmpty(param)){
        	if (param.get("id")!=null && !("").equals(param.get("id").toString())) {
        	    logger.info("{}查询推广管理列表 start...{}", param);
        	    TExtensionInfo tExtensionInfo=new TExtensionInfo();
            	tExtensionInfo.setId(Integer.valueOf(param.get("id").toString()));
            	tExtensionInfo=tExtensionInfoService.selectOne(tExtensionInfo);
        	    logger.info("{}查询推广管理列表 end...{}", tExtensionInfo);
        	    modelMap.addAttribute("extensionInfo", tExtensionInfo);
        	    modelMap.addAttribute("types", "编辑");
            }else{
            	 modelMap.addAttribute("types", "添加");
            }
        }
 	return "/systemSetting/extensioninfo/edit";    
}
 
    @RequestMapping(value = "/detail")
    public Object detail(ModelMap modelMap,HttpServletRequest request) {
        String id = request.getParameter("id");
        if(DataUtil.isNotEmpty(id)){
        	TExtensionInfo tExtensionInfo=new TExtensionInfo();
        	tExtensionInfo.setId(Integer.valueOf(id));
        	tExtensionInfo=tExtensionInfoService.selectOne(tExtensionInfo);
        	modelMap.addAttribute("extensionInfo",tExtensionInfo);
        }
        return "/systemSetting/extensioninfo/detail";    
    }
 
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
			logger.info("删除推广数据，删除主键为：" + ids);
			if(DataUtil.isNotEmpty(ids)){
				String[] strs=ids.split(",");
				for (String string : strs) {
					int id=Integer.valueOf(string);
					//删除推广表
					tExtensionInfoService.delete(id);
				}
			}
			
				return setSuccessResponse(modelMap);  
	}
	@RequestMapping(value = "/addOrUpdata")
	@ResponseBody
	public Object addOrUpdata(ModelMap modelMap,@RequestParam Map<String, Object> param) {
		tExtensionInfoService.addOrUpdata(param);

		return setSuccessResponse(modelMap);
	}

}