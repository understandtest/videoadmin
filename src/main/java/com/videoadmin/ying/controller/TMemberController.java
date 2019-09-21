package com.videoadmin.ying.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.ying.po.TMemberLogin;
import com.videoadmin.ying.service.TMemberLoginService;
import com.videoadmin.ying.service.TMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin/member")
public class TMemberController extends BaseController{
	
	@Autowired
	private TMemberService tMemberService;
	@Autowired
	private TMemberLoginService tMemberLoginService;
	
	@RequestMapping("/list")
	public String sysUserList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		long start = System.currentTimeMillis();
		logger.info("{}获取会员信息list入参:",paraMap);
		Page<?> page = tMemberService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取会员信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfoAndShowSelect(page));
		long end = System.currentTimeMillis();
		logger.info("用户分页查询时间为{}",(end-start)/1000.0);
		return "/memberManager/memberInfo/list";
	}

 
    @RequestMapping(value = "/detail")
    public Object detail(ModelMap modelMap,HttpServletRequest request) {
        String id = request.getParameter("id");
        if(DataUtil.isNotEmpty(id)){
        	Map<String,Object> param = new HashMap<String,Object>();
        	param.put("id", id);
        	 Map<String, Object> map=tMemberService.getMemberInfo(param);
        	modelMap.addAttribute("member",map);
        }
        return "/memberManager/memberInfo/detail";    
    }
 
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
			logger.info("删除会员数据，删除主键为：" + ids);
			Map<String,Object> map=new HashMap<String,Object>();
			if(DataUtil.isNotEmpty(ids)){
				String[] strs=ids.split(",");
				for (String string : strs) {
					int id=Integer.valueOf(string);
					//删除会员登录表
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("memberId", id);
					Integer loginid=tMemberLoginService.getMemberLoginId(param);
					tMemberLoginService.delete(loginid);
					//删除会员表
					tMemberService.delete(id);
				}
			}
			
				return setSuccessResponse(modelMap);  
	}
	@RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        if(DataUtil.isNotEmpty(param)){
        	if (param.get("id")!=null && !("").equals(param.get("id").toString())) {
        	    logger.info("{}查询会员管理列表 start...{}", param);
            	 Map<String, Object> map=tMemberService.getMemberInfo(param);
        	    logger.info("{}查询会员管理列表 end...{}", map);
        	    modelMap.addAttribute("member", map);
        	    modelMap.addAttribute("types", "编辑");
            }else{
            	 modelMap.addAttribute("types", "添加");
            }
        }
 	return "/memberManager/memberInfo/edit";    
}
	@RequestMapping(value = "/addOrUpdata")
	@ResponseBody
	public Object addOrUpdata(ModelMap modelMap,@RequestParam Map<String, Object> param) {
		tMemberService.addOrUpdata(param);

		return setSuccessResponse(modelMap);
	}
	@RequestMapping(value = "/{type}Batch")
	@ResponseBody
	public Object pushOrLower(ModelMap modelMap, @RequestParam Map<String, Object> param,@PathVariable String type) {
			logger.info("封存/解封数据，主键为：" + param);
			String isDeviceEnable="";
			String isEnable="";
			if(param.get("isDeviceEnable")!=null&& !("").equals(param.get("isDeviceEnable").toString())){
				isDeviceEnable=param.get("isDeviceEnable").toString();
			}
			if(param.get("isEnable")!=null&& !("").equals(param.get("isEnable").toString())){
				isEnable=param.get("isEnable").toString();
			}
			 if (param.get("ids")!=null&& !("").equals(param.get("ids").toString()))
		        {
				 String ids=param.get("ids").toString();
				 String[] strs=ids.split(",");
					for (String string : strs) {
						TMemberLogin tMemberLogin=new TMemberLogin();
						Integer id=Integer.valueOf(string);
						tMemberLogin.setId(id);
						if(type.equals("device")){
							if(!("").equals(isDeviceEnable)){
								tMemberLogin.setIsDeviceEnable(Integer.valueOf(isDeviceEnable));
							}
						}
						if(type.equals("number")){
							if(!("").equals(isEnable)){
								tMemberLogin.setIsEnable(Integer.valueOf(isEnable));
							}
						}
						tMemberLogin.setUpdateTime(new Date());
						tMemberLoginService.update(tMemberLogin);
					}
		        }
			
				return setSuccessResponse(modelMap);  
	}
}