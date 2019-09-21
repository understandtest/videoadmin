package com.videoadmin.ying.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.utils.PropertiesUtil;
import com.videoadmin.utils.redis.CacheUtil;
import com.videoadmin.ying.po.TTags;
import com.videoadmin.ying.service.TTagsService;


@Controller
@RequestMapping("/admin/tag")
public class TTagsController extends BaseController{
	
	@Autowired
	private TTagsService tTagsService;


	@RequestMapping("/list")
	public String sysUserList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取标签信息list入参:",paraMap);
		Page<?> page = tTagsService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取标签信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/videomanager/tag/list";
	}

    @RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        if(DataUtil.isNotEmpty(param)){
        	if (param.get("id")!=null && !("").equals(param.get("id").toString())) {
        	    logger.info("{}查询标签列表 start...{}", param);
        	    Map<String, Object> map=tTagsService.getTagInfo(param);
        	    logger.info("{}查询标签列表 end...{}", map);
        	    modelMap.addAttribute("tag", map);
        	    modelMap.addAttribute("types", "编辑");
            }else{
            	 modelMap.addAttribute("types", "添加");
            }
        }
        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
        return "/videomanager/tag/edit";    
			
    }
 
    @RequestMapping(value = "/detail")
    public Object detail(ModelMap modelMap,HttpServletRequest request) {
        String id = request.getParameter("id");
        if(DataUtil.isNotEmpty(id)){
        	Map<String,Object> param = new HashMap<String,Object>();
        	param.put("id", id);
        	 Map<String, Object> map=tTagsService.getTagInfo(param);
        	modelMap.addAttribute("tag",map);
        }
        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
        return "/videomanager/tag/detail";    
    }
 
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
			logger.info("删除标签数据，删除主键为：" + ids);
			Map<String,Object> map=new HashMap<String,Object>();
			if(DataUtil.isNotEmpty(ids)){
				String[] strs=ids.split(",");
				for (String string : strs) {
					int id=Integer.valueOf(string);
					tTagsService.delete(id);
				}
			}
			
				return setSuccessResponse(modelMap);  
	}
   
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object updateUser(ModelMap modelMap,@RequestBody Map<String, Object> param) {
	    
	    if (param!=null)
        {
	      //更新当前表
	       TTags tags=new TTags();
	        if (param.get("name")!=null&& !("").equals(param.get("name").toString()))
	        {
	        	tags.setName(param.get("name").toString());
	        }
	        if (param.get("picUrl")!=null&& !("").equals(param.get("picUrl").toString()))
	        {
	        	tags.setPicUrl(param.get("picUrl").toString());
	        }
	        if (param.get("picType")!=null&& !("").equals(param.get("picType").toString()))
	        {
	        	tags.setPicType(Integer.valueOf(param.get("picType").toString()));
	        }
	        if (param.get("tagTypeId")!=null&& !("").equals(param.get("tagTypeId").toString()))
	        {
	        	tags.setTagTypeId(Integer.valueOf(param.get("tagTypeId").toString()));
	        }
	     
	        if(null != param.get("id") && !("").equals(param.get("id").toString())){
				//更新
	        	tags.setId(Integer.valueOf(param.get("id").toString()));
	        	//login.setUpdateBy(getCurrUser().getUserId());
	        	tags.setUpdateTime(new Date());
			}else{
				//新增
				//login.setCreateBy(getCurrUser().getUserId());
				tags.setCreateTime(new Date());
			}
            
	        //更新标签表
            logger.info("{}更新标签表 start...{}", tags);
            tTagsService.update(tags);
            CacheUtil.getCache().del("indexInfo");
	        return setSuccessResponse(modelMap,tags);
        }
		return setSuccessResponse(modelMap);
	}

    @PostMapping("/validateName")
    @ResponseBody
    public Object validateName(ModelMap modelMap, @RequestParam Map<String, Object> param) {
    	logger.info("{}验证标签名称是否存在入参:",param);
    	if(param.get("tagTypeId")!=null&& !("").equals(param.get("tagTypeId"))){
    		Integer id = tTagsService.validateName(param);
    		logger.info("{}验证标签名称是否存在出参:",id);
    		if(id > 0){
    			return "false";
    		}
    	}else{
    		return "false";
    	}
		return "true";
    }
}