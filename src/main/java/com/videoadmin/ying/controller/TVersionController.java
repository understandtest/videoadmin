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
import com.videoadmin.ying.po.TVersion;
import com.videoadmin.ying.service.TVersionService;


@Controller
@RequestMapping("/admin/version")
public class TVersionController extends BaseController{
	
	@Autowired
	private TVersionService tVersionService;
	
	@RequestMapping("/list")
	public String sysUserList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取版本信息list入参:",paraMap);
		Page<?> page = tVersionService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取版本信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/systemSetting/version/list";
	}
	@RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        if(DataUtil.isNotEmpty(param)){
        	if (param.get("id")!=null && !("").equals(param.get("id").toString())) {
        	    logger.info("{}查询版本管理列表 start...{}", param);
        	    TVersion tVersion=new TVersion();
        	    tVersion.setId(Integer.valueOf(param.get("id").toString()));
        	    tVersion=tVersionService.selectOne(tVersion);
        	    logger.info("{}查询版本管理列表 end...{}", tVersion);
        	    modelMap.addAttribute("version", tVersion);
        	    modelMap.addAttribute("types", "编辑");
            }else{
            	 modelMap.addAttribute("types", "添加");
            }
        }
 	return "/systemSetting/version/edit";    
}
 
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
			logger.info("删除版本数据，删除主键为：" + ids);
			if(DataUtil.isNotEmpty(ids)){
				String[] strs=ids.split(",");
				for (String string : strs) {
					int id=Integer.valueOf(string);
					//删除版本表
					tVersionService.delete(id);
				}
			}
			
				return setSuccessResponse(modelMap);  
	}
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object addOrUpdata(ModelMap modelMap,@RequestParam Map<String, Object> param) {
		tVersionService.addOrUpdata(param);

		return setSuccessResponse(modelMap);
	}
	@RequestMapping(value = "/detail")
    public Object detail(ModelMap modelMap,HttpServletRequest request) {
        String id = request.getParameter("id");
        if(DataUtil.isNotEmpty(id)){
        	 TVersion tVersion=new TVersion();
        	 tVersion.setId(Integer.valueOf(Integer.valueOf(id)));
        	 tVersion=tVersionService.selectOne(tVersion);
        	modelMap.addAttribute("version",tVersion);
        }
        return "/systemSetting/version/detail";    
    }

}