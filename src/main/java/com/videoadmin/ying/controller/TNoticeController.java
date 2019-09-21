package com.videoadmin.ying.controller;

import java.util.List;
import java.util.Map;

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
import com.videoadmin.ying.po.TNotice;
import com.videoadmin.ying.service.TNoticeService;


@Controller
@RequestMapping("/admin/notice")
public class TNoticeController extends BaseController{
	
	@Autowired
	private TNoticeService tNoticeService;
	
	@RequestMapping("/list")
	public String sysUserList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取公告信息list入参:",paraMap);
		Page<?> page = tNoticeService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取公告信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/systemSetting/notice/list";
	}
	@RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        if(DataUtil.isNotEmpty(param)){
        	if (param.get("id")!=null && !("").equals(param.get("id").toString())) {
        	    logger.info("{}查询公告管理列表 start...{}", param);
        	    TNotice tNotice=new TNotice();
        	    tNotice.setId(Integer.valueOf(param.get("id").toString()));
        	    tNotice=tNoticeService.selectOne(tNotice);
        	    logger.info("{}查询公告管理列表 end...{}", tNotice);
        	    modelMap.addAttribute("notice", tNotice);
        	    modelMap.addAttribute("types", "编辑");
            }else{
            	 modelMap.addAttribute("types", "添加");
            }
        }
 	return "/systemSetting/notice/edit";    
}
 
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
			logger.info("删除公告数据，删除主键为：" + ids);
			if(DataUtil.isNotEmpty(ids)){
				String[] strs=ids.split(",");
				for (String string : strs) {
					int id=Integer.valueOf(string);
					//删除公告表
					tNoticeService.delete(id);
				}
			}
			
				return setSuccessResponse(modelMap);  
	}
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object addOrUpdata(ModelMap modelMap,@RequestParam Map<String, Object> param) {
		tNoticeService.addOrUpdata(param);

		return setSuccessResponse(modelMap);
	}
	@PostMapping("/validateName")
    @ResponseBody
    public Object validateName(ModelMap modelMap, @RequestParam Map<String, Object> param) {
    	logger.info("{}验证公告标题是否存在入参:",param);
    	Integer id = tNoticeService.validateName(param);
		logger.info("{}验证公告标题是否存在出参:",id);
		if(id > 0){
			return "false";
		}
		return "true";
    }

}