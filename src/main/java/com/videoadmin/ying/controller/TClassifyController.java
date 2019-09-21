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
import com.videoadmin.ying.po.TClassify;
import com.videoadmin.ying.service.TClassifyService;


@Controller
@RequestMapping("/admin/classify")
public class TClassifyController extends BaseController{
	
	@Autowired
	private TClassifyService tClassifyService;


	@RequestMapping("/list")
	public String sysUserList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取分类list入参:",paraMap);
		Page<?> page = tClassifyService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取分类list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/videomanager/classify/list";
	}

    @RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        if(DataUtil.isNotEmpty(param)){
        	if (param.get("id")!=null && !("").equals(param.get("id").toString())) {
        	    logger.info("{}查询分类列表 start...{}", param);
        	    TClassify tClassify=new TClassify();
        	    tClassify.setId(Integer.valueOf(param.get("id").toString()));
        	    tClassify=tClassifyService.selectOne(tClassify);
        	    logger.info("{}查询分类列表 end...{}", tClassify);
        	    modelMap.addAttribute("classify", tClassify);
        	    modelMap.addAttribute("types", "编辑");
            }else{
            	 modelMap.addAttribute("types", "添加");
            }
        }
        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
        return "/videomanager/classify/edit";    
			
    }
 
    @RequestMapping(value = "/detail")
    public Object detail(ModelMap modelMap,HttpServletRequest request) {
        String id = request.getParameter("id");
        if(DataUtil.isNotEmpty(id)){
        	TClassify tClassify=new TClassify();
    	    tClassify.setId(Integer.valueOf(id));
    	    tClassify=tClassifyService.selectOne(tClassify);
        	modelMap.addAttribute("classify",tClassify);
        }
        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
        return "/videomanager/classify/detail";    
    }
 
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
			logger.info("删除分类数据，删除主键为：" + ids);
			Map<String,Object> map=new HashMap<String,Object>();
			if(DataUtil.isNotEmpty(ids)){
				String[] strs=ids.split(",");
				for (String string : strs) {
					int id=Integer.valueOf(string);
					tClassifyService.delete(id);
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
	      TClassify tClassify=new TClassify();
	        if (param.get("name")!=null&& !("").equals(param.get("name").toString()))
	        {
	        	tClassify.setName(param.get("name").toString());
	        }
	        if (param.get("iconType")!=null&& !("").equals(param.get("iconType").toString()))
	        {
	        	tClassify.setIconType(Integer.valueOf(param.get("iconType").toString()));
	        }
	        if (param.get("classifyIcon")!=null&& !("").equals(param.get("classifyIcon").toString()))
	        {
	        	tClassify.setClassifyIcon(param.get("classifyIcon").toString());
	        }
	     
	        if(null != param.get("id") && !"".equals(param.get("id").toString())){
				//更新
	        	tClassify.setId(Integer.valueOf(param.get("id").toString()));
	        	//login.setUpdateBy(getCurrUser().getUserId());
	        	tClassify.setUpdateTime(new Date());
			}else{
				//新增
				//login.setCreateBy(getCurrUser().getUserId());
				tClassify.setCreateTime(new Date());
			}
            
	        //更新分类表
            logger.info("{}更新分类表 start...{}", tClassify);
            tClassifyService.update(tClassify);
            CacheUtil.getCache().del("indexInfo");
	        return setSuccessResponse(modelMap,tClassify);
        }
		return setSuccessResponse(modelMap);
	}

    @PostMapping("/validateName")
    @ResponseBody
    public Object validateName(ModelMap modelMap, @RequestParam Map<String, Object> param) {
    	logger.info("{}验证分类名称是否存在入参:",param);
    	Integer id = tClassifyService.validateName(param);
		logger.info("{}验证分类名称是否存在出参:",id);
		if(id > 0){
			return "false";
		}
		return "true";
    }
}