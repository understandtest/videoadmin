package com.videoadmin.ying.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.CellType;
import jxl.biff.DisplayFormat;
import jxl.write.NumberFormat;

import org.apache.commons.beanutils.BeanUtils;
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
import com.videoadmin.utils.DownloadExcelUtil;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.ying.po.TRechargeCode;
import com.videoadmin.ying.po.TVip;
import com.videoadmin.ying.service.TAgentService;
import com.videoadmin.ying.service.TRechargeCodeService;
import com.videoadmin.ying.service.TVipService;

/**
 * 
 *
 */
@Controller
@RequestMapping("/admin/rechargeCode")
public class TRechargeCodeController extends BaseController{

	@Autowired
	private TRechargeCodeService tRechargeCodeService;
	
	
	@Autowired
	private TAgentService tAgentService;
	
	@Autowired
	private TVipService tVipService;
	
	@RequestMapping("/list")
	public String list(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取充值码信息list入参:",paraMap);	
		//获取代理商信息
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = tAgentService.selectAgentList();
		modelMap.put("agentList", list);
		Page<?> page = tRechargeCodeService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取充值码信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/rechargecode/list";
	}
	
	
	@RequestMapping(value = "/toEdit")
    public String toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = tAgentService.selectAgentList();
		modelMap.put("agentList", list);
    	if (param != null && !"".equals(param)&& param.get("id")!=null && !("").equals(param.get("id").toString())) {
    	    logger.info("{}查询充值码列表 start...{}", param);
    	    TRechargeCode trc = new TRechargeCode();
    	    trc.setId(Integer.valueOf(param.get("id").toString()));
    	    trc=tRechargeCodeService.selectOne(trc);
    	    logger.info("{}查询充值码列表 end...{}");
    	    modelMap.addAttribute("rechargeCode", trc);
    	    modelMap.addAttribute("types", "编辑");
        }else{
        	/*String rechargeCodeValue = DataUtil.getOderNumber();
        	modelMap.put("rechargeCodeValue", rechargeCodeValue);*/
        	modelMap.addAttribute("types", "添加");
        }
        return "/rechargecode/edit";    
    }
	
	
	@RequestMapping(value = "/update")
	@ResponseBody
	@Transactional
	public Object updateUser(ModelMap modelMap,@RequestBody TRechargeCode trc) {
 		if(trc != null && !"".equals(trc))
 		{
 			if(trc.getCodeNum() != null && !"".equals(trc.getCodeNum()))
 			{
 				//根据卡片类型获取VIPID
 	 			TVip vip = new TVip();
 	 			vip.setCardType(trc.getCardType());
 	 			vip = tVipService.selectOne(vip);
 	 			if(vip != null && !"".equals(vip))
 	 			{
 	 				trc.setVipId(vip.getId());
 	 			}
 	 			Date date = new Date();
 	 			if(trc.getAgentId() != null && !"".equals(trc.getAgentId()))
 	 			{
 	 				trc.setSalesTime(date);
 	 				trc.setIsSales(2);
 	 			}
 	 			trc.setGenerationTime(date);
 	 			int i = Integer.valueOf(trc.getCodeNum());
 	 			if(i>0)
 	 			{
 	 				for(int j=0;j<i;j++)
 	 				{
 	 					String rechargeCodeValue = DataUtil.getOderNumber();
 	 					TRechargeCode thk = new TRechargeCode();
 	 					try {
							BeanUtils.copyProperties(thk,trc);
						} catch (Exception e) {
							logger.info("类型复制异常,异常信息:"+e.getMessage());
						}
 	 					thk.setRechargeCode(rechargeCodeValue);
 	 					tRechargeCodeService.update(thk);
 	 				}
 	 			}
 			}
 			else
 			{
 				Date date = new Date();
 	 			if(trc.getAgentId() != null && !"".equals(trc.getAgentId()))
 	 			{
 	 				trc.setSalesTime(date);
 	 				trc.setIsSales(2);
 	 			}
 	 			trc.setGenerationTime(date);
 	 			//根据卡片类型获取VIPID
 	 			TVip vip = new TVip();
 	 			vip.setCardType(trc.getCardType());
 	 			vip = tVipService.selectOne(vip);
 	 			if(vip != null && !"".equals(vip))
 	 			{
 	 				trc.setVipId(vip.getId());
 	 			}
 	 			tRechargeCodeService.update(trc);
 			}
 			
 		}
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
					tRechargeCodeService.delete(id);
				}
			}
		}
		return setSuccessResponse(modelMap);  
	}
	
	@RequestMapping("/exportRechargeCode")
	public void exportRechargeCode(HttpServletResponse response,@RequestParam Map<String, Object> paramMap) throws Exception
	{
		 List<LinkedHashMap<String,Object>> list = new ArrayList<LinkedHashMap<String,Object>>();
		 list =tRechargeCodeService.selectListExport(paramMap);
		 DownloadExcelUtil deu = new DownloadExcelUtil(response, "批量下载充值码", "批量下载充值码");
		 deu.setExcelListTitle(new String[]{"所属代理","充值码","卡片类型"});
		 List<CellType> ctList = new ArrayList<>();
		 ctList.add(CellType.STRING_FORMULA);
         ctList.add(CellType.STRING_FORMULA);
         ctList.add(CellType.STRING_FORMULA);
         List<DisplayFormat> nfList = new ArrayList<DisplayFormat>();
         nfList.add(null);
         nfList.add(null);
         nfList.add(null);
         deu.addRows(list, ctList.toArray(new CellType[0]), nfList.toArray(new NumberFormat[0]));
         deu.reportExcel();
	}
	
	
}
