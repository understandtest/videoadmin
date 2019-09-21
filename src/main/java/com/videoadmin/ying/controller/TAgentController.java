package com.videoadmin.ying.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.MD5Util;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.utils.SysConstants;
import com.videoadmin.ying.po.SysLogin;
import com.videoadmin.ying.po.SysUserRoleRel;
import com.videoadmin.ying.po.TAgent;
import com.videoadmin.ying.service.TAgentService;

@Controller
@RequestMapping("/admin/agent")
public class TAgentController extends BaseController{

	@Autowired
	private TAgentService tAgentService;
	
	@RequestMapping("/list")
	public String list(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取代理商信息list入参:",paraMap);
		Page<?> page = tAgentService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取代理商信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "agent/list";
	}
	
	
	 @RequestMapping(value = "/toEdit")
	    public String toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        	if (param != null && !"".equals(param)&&param.get("id")!=null && !("").equals(param.get("id").toString())) {
        	    logger.info("{}查询代理列表 start...{}", param);
        	    TAgent agent = new TAgent();
        	    agent.setId(Integer.valueOf(param.get("id").toString()));
        	    agent=tAgentService.selectOne(agent);
        	    logger.info("{}查询代理列表 end...{}");
        	    modelMap.addAttribute("agent", agent);
        	    modelMap.addAttribute("types", "编辑");
            }else{
            	 modelMap.addAttribute("types", "添加");
            }
	        return "/agent/edit";    
	    }
	 
	 
	 	@RequestMapping(value = "/update")
		@ResponseBody
		@Transactional
		public Object updateUser(ModelMap modelMap,@RequestBody TAgent agent) {
	 		tAgentService.update(agent);
			return setSuccessResponse(modelMap);
		}
	 	
	 	
	 	
	 	@RequestMapping(value = "/delete")
		@ResponseBody
		@Transactional
		public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
			logger.info("删除代理数据，删除主键为：" + ids);
			String[] str = ids.split(",");
			if (str != null && !"".equals(str) && str.length > 0) {
				for (String ss : str) {
					if (ss != null && !"".equals(ss)) {
						Integer id = Integer.valueOf(ss);
						tAgentService.delete(id);
					}
				}
			}
			return setSuccessResponse(modelMap);  
		}
	 	
	 	
	 	@RequestMapping(value = "/detail")
	    public String detail(ModelMap modelMap,HttpServletRequest request) {
	        String id = request.getParameter("id");
	        if(DataUtil.isNotEmpty(id)){
	        	TAgent agent = new TAgent();
        	    agent.setId(Integer.valueOf(id));
        	    agent=tAgentService.selectOne(agent);
	        	modelMap.addAttribute("agent",agent);
	        }
	        return "/agent/detail";    
	    }
}
