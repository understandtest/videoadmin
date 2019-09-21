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
import com.videoadmin.ying.po.TCollectionChannel;
import com.videoadmin.ying.service.TCollectionChannelService;


@Controller
@RequestMapping("/admin/collectionChannel")
public class TCollectionChannelController extends BaseController{
	
	@Autowired
	private TCollectionChannelService tChannelService;


	@RequestMapping("/list")
	public String sysUserList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取采集渠道信息list入参:",paraMap);
		Page<?> page = tChannelService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取采集渠道信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/videomanager/collectionChannel/list";
	}

    @RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        if(DataUtil.isNotEmpty(param)){
        	if (param.get("id")!=null && !("").equals(param.get("id").toString())) {
        	    logger.info("{}查询采集渠道列表 start...{}", param);
        	    TCollectionChannel tcChannel=new TCollectionChannel();
        	    tcChannel.setId(Integer.valueOf(param.get("id").toString()));
        	    tcChannel=tChannelService.selectOne(tcChannel);
        	    logger.info("{}查询采集渠道列表 end...{}", tcChannel);
        	    modelMap.addAttribute("collectionChannel", tcChannel);
        	    modelMap.addAttribute("types", "编辑");
            }else{
            	 modelMap.addAttribute("types", "添加");
            }
        }
        	return "/videomanager/collectionChannel/edit";    
			
    }
 
    @RequestMapping(value = "/detail")
    public Object detail(ModelMap modelMap,HttpServletRequest request) {
        String id = request.getParameter("id");
        if(DataUtil.isNotEmpty(id)){
        	 TCollectionChannel tcChannel=new TCollectionChannel();
        	 tcChannel.setId(Integer.valueOf(id));
        	 tcChannel=tChannelService.selectOne(tcChannel);
        	modelMap.addAttribute("collectionChannel",tcChannel);
        }
        return "/videomanager/collectionChannel/detail";    
    }
 
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
			logger.info("删除采集渠道数据，删除主键为：" + ids);
			Map<String,Object> map=new HashMap<String,Object>();
			if(DataUtil.isNotEmpty(ids)){
				String[] strs=ids.split(",");
				for (String string : strs) {
					int id=Integer.valueOf(string);
					tChannelService.delete(id);
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
	       TCollectionChannel tcChannel=new TCollectionChannel();
	        if (param.get("channelName")!=null&& !("").equals(param.get("channelName").toString()))
	        {
	        	tcChannel.setChannelName(param.get("channelName").toString());
	        }
	       
	        if(null != param.get("id") && !"".equals(param.get("id").toString())){
				//更新
	        	tcChannel.setId(Integer.valueOf(param.get("id").toString()));
	        	//login.setUpdateBy(getCurrUser().getUserId());
	        	tcChannel.setUpdateTime(new Date());
			}else{
				//新增
				//login.setCreateBy(getCurrUser().getUserId());
				tcChannel.setCreateTime(new Date());
			}
            
	        //更新采集渠道表
            logger.info("{}更新采集渠道表 start...{}", tcChannel);
            tChannelService.update(tcChannel);
            
	        return setSuccessResponse(modelMap,tcChannel);
        }
		return setSuccessResponse(modelMap);
	}

    @PostMapping("/validateName")
    @ResponseBody
    public Object validateName(ModelMap modelMap, @RequestParam Map<String, Object> param) {
    	logger.info("{}验证采集渠道名称是否存在入参:",param);
    	Integer id = tChannelService.validateName(param);
		logger.info("{}验证采集渠道名称是否存在出参:",id);
		if(id > 0){
			return "false";
		}
		return "true";
    }
}