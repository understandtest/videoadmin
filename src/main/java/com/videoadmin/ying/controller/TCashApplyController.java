package com.videoadmin.ying.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.ying.po.TCashApply;
import com.videoadmin.ying.service.TCashApplyService;


@Controller
@RequestMapping("/admin/cashApply")
public class TCashApplyController extends BaseController{

	@Autowired
	private TCashApplyService tCashApplyService;
	
	@RequestMapping("/list")
	public String list(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取提现申请信息list入参:",paraMap);
		Page<?> page = tCashApplyService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取提现申请信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/cashApply/list";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Object update(ModelMap modelMap,@RequestParam String ids,@RequestParam String payStatus)
	{
		String[] str = ids.split(",");
		if (str != null && !"".equals(str) && str.length > 0) {
			for (String ss : str) {
				if (ss != null && !"".equals(ss)) {
					Integer id = Integer.valueOf(ss);
					TCashApply tc = new TCashApply();
					tc.setId(id);
					tc.setPayStatus(Integer.valueOf(payStatus));
					tc.setApproveTime(new Date());
					tCashApplyService.update(tc);
				}
			}
			modelMap.put("resCode", "1");
		}
		return setSuccessResponse(modelMap); 
	}
	
}
