package com.videoadmin.ying.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.videoadmin.utils.DoubleUtil;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.ying.po.TExtendPrice;
import com.videoadmin.ying.po.TExtensionHistory;
import com.videoadmin.ying.po.TMember;
import com.videoadmin.ying.po.TReport;
import com.videoadmin.ying.po.TVip;
import com.videoadmin.ying.po.TVipHistory;
import com.videoadmin.ying.service.TExtendPriceService;
import com.videoadmin.ying.service.TExtensionHistoryService;
import com.videoadmin.ying.service.TMemberService;
import com.videoadmin.ying.service.TRateService;
import com.videoadmin.ying.service.TReportService;
import com.videoadmin.ying.service.TVipHistoryService;
import com.videoadmin.ying.service.TVipService;


@Controller
@RequestMapping("/admin/vipHistory")
public class TVipHistoryController extends BaseController{
	
	@Autowired
	private TVipHistoryService tVipHistoryService;
    @Autowired
    private TVipService tVipService;
    @Autowired
	private TMemberService tMemberService;
    @Autowired
    private TReportService tReportService;
    
    @Autowired
	private TRateService tRateService;
    
    @Autowired
    private TExtensionHistoryService tExtensionHistoryService;
    
    @Autowired
    private TExtendPriceService tExtendPriceService;
    
	@RequestMapping("/list")
	public String sysUserList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取购买VIP记录信息list入参:",paraMap);
		Page<?> page = tVipHistoryService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取购买VIP记录信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/fundManager/viphistory/list";
	}
	@RequestMapping("/ajaxCardTypeList")
	@ResponseBody
	public Object ajaxCardTypeList(ModelMap modelMap){
		logger.info("{}ajax查询VIP 类型list信息start...");
		Map<String,Object> params=new HashMap<>();
		List<Map<String,Object>> list =tVipService.selectListPage(params);
		logger.info("{}ajax查询VIP类型 list信息end...出参...",list);
		modelMap.put("list", list);
		return setSuccessResponse(modelMap);
	}
	/**
	 * 给用户补单
	 * @param modelMap
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/ajaxFillForm")
	@ResponseBody
	public Object  ajaxFillForm(ModelMap modelMap,@RequestParam(required = true) String orderIds)
	{
		logger.info("补单操作开始,入参:"+orderIds);
		String[] orderIdArray = orderIds.split(",");
		for(String orderId:orderIdArray)
		{
			TVipHistory tvip = new TVipHistory();
			Integer vipId = Integer.valueOf(orderId);
			tvip.setId(vipId);
			tvip = tVipHistoryService.selectOne(tvip);
			if(tvip != null && !"".equals(tvip))
			{
				if(tvip.getPayStatus().intValue()==1)
				{
					tvip.setPayTime(new Date());
					tvip.setPayStatus(2);
					tVipHistoryService.update(tvip);
					//更新用户VIP，及其使用期限
					TMember tmb = new TMember();
					tmb.setId(tvip.getMemberId());
					tmb = tMemberService.selectOne(tmb);
					tmb.setIsVip(1);
					tmb.setVipId(tvip.getVipId());
					
					TVip vip = new TVip();
					vip.setId(tvip.getVipId());
					vip = tVipService.selectOne(vip);
					Date oldDay = tmb.getVipDate();
					if(oldDay == null || "".equals(oldDay))
					{
						oldDay = new Date();
					}
					Calendar ca = Calendar.getInstance();
					ca.setTime(oldDay);
					if(vip.getCardType().intValue()==1)
					{
						ca.add(Calendar.DATE, 30);
					}
					else if(vip.getCardType().intValue()==2)
					{
						ca.add(Calendar.DATE, 90);;
					}
					else if(vip.getCardType().intValue()==3)
					{
						ca.add(Calendar.DATE, 365);
					}
//					TVip tVip = new TVip();
//					tVip.setId(Integer.valueOf(sf[2]));
//					tVip = tVipService.selectOne(tVip);
//					tmb.setCacheNum(tVip.getCacheNum());
					Date cc = ca.getTime();
					tmb.setVipDate(cc);
					if(tvip.getIsUseCron() == 2)
					{
						if(tvip.getUseCron().intValue()>tmb.getCronNum().intValue())
						{
							modelMap.put("retCode", "-1");
							modelMap.put("retMsg", "客户钻石数不足，无法抵扣，不支持补单");
							return setSuccessResponse(modelMap);
						}
						int newCrom = tmb.getCronNum()-tvip.getUseCron();
						tmb.setCronNum(newCrom);
					}
					tMemberService.update(tmb);
					//更改充值
					SimpleDateFormat  fformat = new SimpleDateFormat("yyyy-MM-dd");
					String dd = fformat.format(new Date());
					TReport treport2 = new TReport();
		       		treport2.setActionType(3);
		       		treport2.setCurentDay(dd);
		       		treport2.setMemberId(tvip.getMemberId());
		       		treport2 = tReportService.selectOne(treport2);
		       		if(treport2 == null ||"".equals(treport2))
		       		{
		       			treport2 = new TReport();
			       		treport2.setActionType(3);
			       		treport2.setCurentDay(dd);
			       		treport2.setMemberId(tvip.getMemberId());
			       		treport2.setRechangeNum(1);
			       		treport2.setRechangePrice(tvip.getPayPrice());
		       		}
		       		else
		       		{
		       			int rechageNum = treport2.getRechangeNum()+1;
		       			treport2.setRechangeNum(rechageNum);
		       			Double tf = DoubleUtil.add(treport2.getRechangePrice(), tvip.getPayPrice());
		       			treport2.setRechangePrice(tf);
		       		}
		       		treport2 = tReportService.update(treport2);
		       		
		       		//进行充值分配钻石
		       		//获取后台比例
		       		Map<String,Object> tf = new HashMap<String,Object>();
		    		tf = tRateService.selectRateInfo();
		    		if(tf != null && !"".equals(tf))
		    		{
		    			if(tf.get("rechargeRate") != null && !"".equals(tf.get("rechargeRate")))
		    			{
		    				Double rechargeRate = Double.valueOf(tf.get("rechargeRate").toString());
		    				Double cronNumDouble= DoubleUtil.multiplyForRoundHalfUp(tvip.getPayPrice(),rechargeRate,0);
		    				int cronNumNew =cronNumDouble.intValue();
		    				//获取被推广人的金币
		    				TExtensionHistory tex = new TExtensionHistory();
		    				tex.setExtendId(tvip.getMemberId());
		    				tex=tExtensionHistoryService.selectOne(tex);
		    				if(tex != null && !"".equals(tex))
		    				{
		    					TMember extendMember = new TMember();
		    					extendMember.setId(tex.getMemberId());
		    					extendMember = tMemberService.selectOne(extendMember);
		    					int newCronNum= extendMember.getCronNum()+cronNumNew;
		    					extendMember.setCronNum(newCronNum);
		    					tMemberService.update(extendMember);
		    					//建立推广
		    					TExtendPrice tdp = new TExtendPrice();
		    					tdp.setCronNum(cronNumNew);
		    					tdp.setMemberId(tvip.getMemberId());
		    					tdp.setVipHistoryId(tvip.getId());
		    					tdp.setExtendId(tex.getMemberId());
		    					tdp.setPrice(tvip.getPayPrice());
		    					tdp.setRechargeRate(rechargeRate);
		    					tdp.setName(tex.getNickName());
		    					tdp.setTel(tex.getTel());
		    					tdp.setRechargeTime(new Date());
		    					tExtendPriceService.update(tdp);
		    				}
		    			}
		    		}
				}
			}
		}
		modelMap.put("retCode", "1");
		return setSuccessResponse(modelMap);
	}
	
}