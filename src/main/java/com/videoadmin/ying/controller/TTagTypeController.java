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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.utils.redis.CacheUtil;
import com.videoadmin.ying.po.TTagType;
import com.videoadmin.ying.service.TTagTypeService;


@Controller
@RequestMapping("/admin/tagType")
public class TTagTypeController extends BaseController{
	
	@Autowired
	private TTagTypeService tagTypeService;


	@RequestMapping("/list")
	public String sysUserList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取标签类型信息list入参:",paraMap);
		Page<?> page = tagTypeService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取标签类型信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/videomanager/tagtype/list";
	}

    @RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        if(DataUtil.isNotEmpty(param)){
        	if (param.get("id")!=null && !("").equals(param.get("id").toString())) {
        	    logger.info("{}查询标签类型列表 start...{}", param);
        	    TTagType tagType=new TTagType();
        	    tagType.setId(Integer.valueOf(param.get("id").toString()));
        	    tagType=tagTypeService.selectOne(tagType);
        	    logger.info("{}查询标签类型列表 end...{}", tagType);
        	    modelMap.addAttribute("tagType", tagType);
        	    modelMap.addAttribute("types", "编辑");
            }else{
            	 modelMap.addAttribute("types", "添加");
            }
        }
        	return "/videomanager/tagtype/edit";    
			
    }
 
    @RequestMapping(value = "/detail")
    public Object detail(ModelMap modelMap,HttpServletRequest request) {
        String id = request.getParameter("id");
        if(DataUtil.isNotEmpty(id)){
        	TTagType tagType=new TTagType();
    	    tagType.setId(Integer.valueOf(id));
    	    tagType=tagTypeService.selectOne(tagType);
        	modelMap.addAttribute("tagType",tagType);
        }
        return "/videomanager/tagtype/detail";    
    }
 
	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
			logger.info("删除标签类型数据，删除主键为：" + ids);
			Map<String,Object> map=new HashMap<String,Object>();
			if(DataUtil.isNotEmpty(ids)){
				String[] strs=ids.split(",");
				for (String string : strs) {
					int id=Integer.valueOf(string);
					tagTypeService.delete(id);
				}
			}
			
				return setSuccessResponse(modelMap);  
	}
   
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object updateUser(ModelMap modelMap,@RequestParam Map<String, Object> param) {
	    
	    if (param!=null)
        {
	      //更新当前表
	       TTagType tagType =new TTagType();
	        if (param.get("name")!=null&& !("").equals(param.get("name").toString()))
	        {
	        	tagType.setName(param.get("name").toString());
	        }

	        if(null != param.get("id") && !"".equals(param.get("id").toString())){
				//更新
	        	tagType.setId(Integer.valueOf(param.get("id").toString()));
	        	//login.setUpdateBy(getCurrUser().getUserId());
	        	tagType.setUpdateTime(new Date());
			}else{
				//新增
				//login.setCreateBy(getCurrUser().getUserId());
				tagType.setCreateTime(new Date());
			}
            
	        //更新标签类型表
            logger.info("{}更新标签类型类型表 start...{}", tagType);
            tagTypeService.update(tagType);
            CacheUtil.getCache().del("indexInfo");
	        return setSuccessResponse(modelMap,tagType);
        }
		return setSuccessResponse(modelMap);
	}

    @PostMapping("/validateName")
    @ResponseBody
    public Object validateName(ModelMap modelMap, @RequestParam Map<String, Object> param) {
    	logger.info("{}验证标签类型名称是否存在入参:",param);
    	Integer id = tagTypeService.validateName(param);
		logger.info("{}验证标签类型名称是否存在出参:",id);
		if(id > 0){
			return "false";
		}
		return "true";
    }
}