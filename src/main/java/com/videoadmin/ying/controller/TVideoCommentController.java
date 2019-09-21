package com.videoadmin.ying.controller;

import java.util.HashMap;
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
import com.videoadmin.ying.service.TVideoCommentService;


@Controller
@RequestMapping("/admin/videoComment")
public class TVideoCommentController extends BaseController{
	
	@Autowired
	private TVideoCommentService tVideoCommentService;


	@RequestMapping("/list")
	public String sysUserList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取视频评论信息list入参:",paraMap);
		Page<?> page = tVideoCommentService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取视频评论信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/memberManager/videoComment/list";
	}

 
    @RequestMapping(value = "/detail")
    public Object detail(ModelMap modelMap,HttpServletRequest request) {
        String id = request.getParameter("id");
        if(DataUtil.isNotEmpty(id)){
        	Map<String,Object> param = new HashMap<String,Object>();
        	param.put("id", id);
        	 Map<String, Object> map=tVideoCommentService.getVideoCommentInfo(param);
        	modelMap.addAttribute("videoComment",map);
        }
        return "/memberManager/videoComment/detail";    
    }
 
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
			logger.info("删除视频评论数据，删除主键为：" + ids);
			Map<String,Object> map=new HashMap<String,Object>();
			if(DataUtil.isNotEmpty(ids)){
				String[] strs=ids.split(",");
				for (String string : strs) {
					int id=Integer.valueOf(string);
					tVideoCommentService.delete(id);
				}
			}
			
				return setSuccessResponse(modelMap);  
	}
}