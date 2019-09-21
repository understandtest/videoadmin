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
import com.videoadmin.ying.po.TLevel;
import com.videoadmin.ying.service.TLevelService;


@Controller
@RequestMapping("/admin/level")
public class TLevelController extends BaseController{
	
	@Autowired
	private TLevelService tLevelService;


	@RequestMapping("/list")
	public String sysUserList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取等级信息list入参:",paraMap);
		Page<?> page = tLevelService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取等级信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/memberManager/level/list";
	}

    @RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        if(DataUtil.isNotEmpty(param)){
        	if (param.get("id")!=null && !("").equals(param.get("id").toString())) {
        	    logger.info("{}查询等级列表 start...{}", param);
        	    TLevel tLevel=new TLevel();
        	    tLevel.setId(Integer.valueOf(param.get("id").toString()));
        	    tLevel=tLevelService.selectOne(tLevel);
        	    logger.info("{}查询等级列表 end...{}", tLevel);
        	    modelMap.addAttribute("level", tLevel);
        	    modelMap.addAttribute("types", "编辑");
            }else{
            	 modelMap.addAttribute("types", "添加");
            }
        }
        	return "/memberManager/level/edit";    
			
    }
 
    @RequestMapping(value = "/detail")
    public Object detail(ModelMap modelMap,HttpServletRequest request) {
        String id = request.getParameter("id");
        if(DataUtil.isNotEmpty(id)){
        	TLevel tLevel=new TLevel();
        	tLevel.setId(Integer.valueOf(id));
        	tLevel=tLevelService.selectOne(tLevel);
        	modelMap.addAttribute("level",tLevel);
        }
        return "/memberManager/level/detail";    
    }
 
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
			logger.info("删除等级数据，删除主键为：" + ids);
			Map<String,Object> map=new HashMap<String,Object>();
			if(DataUtil.isNotEmpty(ids)){
				String[] strs=ids.split(",");
				for (String string : strs) {
					int id=Integer.valueOf(string);
					tLevelService.delete(id);
				}
			}
			
				return setSuccessResponse(modelMap);  
	}
   
	@RequestMapping(value = "/addOrUpdata")
	@ResponseBody
	public Object updateUser(ModelMap modelMap,@RequestParam Map<String, Object> param) {
	    
	    if (param!=null)
        {
	      //更新当前表
	       TLevel tLevel=new TLevel();
	        if (param.get("name")!=null&& !("").equals(param.get("name").toString()))
	        {
	        	tLevel.setName(param.get("name").toString());
	        }
	        if (param.get("iconType")!=null&& !("").equals(param.get("iconType").toString()))
	        {
	        	tLevel.setIconType(Integer.valueOf(param.get("iconType").toString()));
	        }
	        if (param.get("leIcon")!=null&& !("").equals(param.get("leIcon").toString()))
	        {
	        	tLevel.setLeIcon(param.get("leIcon").toString());
	        }
	        if (param.get("exNum")!=null&& !("").equals(param.get("exNum").toString()))
	        {
	        	tLevel.setExNum(Integer.valueOf(param.get("exNum").toString()));
	        }
	        if (param.get("dayNum")!=null&& !("").equals(param.get("dayNum").toString()))
	        {
	        	tLevel.setDayNum(Integer.valueOf(param.get("dayNum").toString()));
	        }

	        if (param.get("sortNo")!=null&& !("").equals(param.get("sortNo").toString()))
	        {
	        	tLevel.setSortNo(Integer.valueOf(param.get("sortNo").toString()));
	        }
	       
	        if(null != param.get("id") && !("").equals(param.get("id").toString())){
				//更新
	        	tLevel.setId(Integer.valueOf(param.get("id").toString()));
	        	//login.setUpdateBy(getCurrUser().getUserId());
	        	tLevel.setUpdateTime(new Date());
			}else{
				//新增
				//login.setCreateBy(getCurrUser().getUserId());
				tLevel.setCreateTime(new Date());
			}
            
	        //更新等级表
            logger.info("{}更新等级表 start...{}", tLevel);
            tLevelService.update(tLevel);
            
	        return setSuccessResponse(modelMap,tLevel);
        }
		return setSuccessResponse(modelMap);
	}

    @PostMapping("/validateName")
    @ResponseBody
    public Object validateName(ModelMap modelMap, @RequestParam Map<String, Object> param) {
    	logger.info("{}验证等级名称是否存在入参:",param);
    	Integer id = tLevelService.validateName(param);
		logger.info("{}验证等级名称是否存在出参:",id);
		if(id > 0){
			return "false";
		}
		return "true";
    }
}