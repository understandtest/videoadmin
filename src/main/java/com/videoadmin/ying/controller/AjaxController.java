package com.videoadmin.ying.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.videoadmin.ying.po.*;
import com.videoadmin.ying.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.videoadmin.base.BaseController;

/**
 * 获取异步数据
 * @version 2018-10-8
 * @see AjaxController
 * @since
 */
@Controller
@RequestMapping("/webajax")
public class AjaxController extends BaseController{
  @Autowired
  private SysRoleService sysRoleService;
   @Autowired
   private TTagTypeService tTagTypeService;
   @Autowired
   private TStarService tStarService;
   @Autowired
   private TDataKeyService tDataKeyService;
   @Autowired
   private TClassifyService tClassifyService;;
   @Autowired
   private TCollectionChannelService tCollectionChannelService;
   @Autowired
   private TVideoService tVideoService;
   @Autowired
   private TTagsService tagsService;
   @Autowired
   private TVipService tVipService;
   @Autowired
   private TLevelService tLevelService;
   @Autowired
   private TMemberLoginService tMemberLoginService;

   @Autowired
   private CaricatureClassifyService caricatureClassifyService;


   @RequestMapping("/ajaxCaricatureClassifyList")
   @ResponseBody
   public Object ajaxCaricatureClassifyList(ModelMap modelMap){
	   List<CaricatureClassify> caricatureClassifies = caricatureClassifyService.findAll();
	   modelMap.put("list", caricatureClassifies);
	   return setSuccessResponse(modelMap);
   }


	@RequestMapping("/ajaxUserRoleList")
	@ResponseBody
	public Object ajaxUserRoleList(ModelMap modelMap){
		logger.info("{}ajax查询角色list信息start...");
		Map<String,Object> params=new HashMap<>();
		List<Map<String,Object>> list =sysRoleService.selectRoleList(params);
		logger.info("{}ajax查询角色list信息end...出参...",list);
		modelMap.put("list", list);
		return setSuccessResponse(modelMap);
	}
	@RequestMapping("/ajaxTagTypeIdList")
	@ResponseBody
	public Object ajaxTagTypeIdList(ModelMap modelMap){
		logger.info("{}ajax查询标签类型list信息start...");
		Map<String,Object> params=new HashMap<>();
		List<TTagType> list =tTagTypeService.queryList(params);
		logger.info("{}ajax查询标签类型list信息end...出参...",list);
		modelMap.put("list", list);
		return setSuccessResponse(modelMap);
	}


	@RequestMapping("/ajaxTagTypeList")
	@ResponseBody
	public Object ajaxTagTypeList(ModelMap modelMap){
		List<TTagType> list =tTagTypeService.findAll();
		logger.info("{}ajax查询标签类型list信息end...出参...",list);
		modelMap.put("list", list);
		return setSuccessResponse(modelMap);

	}

	@RequestMapping("/ajaxTagByTyId")
	@ResponseBody
	public Object ajaxTagByTyId(ModelMap modelMap,Integer tagTypeId){
		List<TTags> list = tagsService.getTagListByTpId(tagTypeId);
		modelMap.put("list", list);
		return setSuccessResponse(modelMap);
	}


	@RequestMapping(value="/ajaxBaseData")
	@ResponseBody
	public Object ajaxBaseData(ModelMap modelMap, @RequestParam String key){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//查出基础数据
		paramMap.clear();
		paramMap.put("dataKeyType", key);
		logger.info("开始查询基础数据 , 入参{}",paramMap);
		List<TDataKey> list=tDataKeyService.queryList(paramMap);
		logger.info("查询基础数据 , 出参{}",list);
		return setSuccessResponse(modelMap, list);
	}
	
	@RequestMapping("/ajaxStarList")
	@ResponseBody
	public Object ajaxStarList(ModelMap modelMap){
		logger.info("{}ajax查询明星list信息start...");
		Map<String,Object> params=new HashMap<>();
		List<TStar> list =tStarService.queryList(params);
		logger.info("{}ajax查询明星list信息end...出参...",list);
		modelMap.put("list", list);
		return setSuccessResponse(modelMap);
	}
	@RequestMapping("/ajaxClassifyList")
	@ResponseBody
	public Object ajaxClassifyList(ModelMap modelMap){
		logger.info("{}ajax查询分类list信息start...");
		Map<String,Object> params=new HashMap<>();
		List<TClassify> list =tClassifyService.queryList(params);
		logger.info("{}ajax查询分类list信息end...出参...",list);
		modelMap.put("list", list);
		return setSuccessResponse(modelMap);
	}
	@RequestMapping("/ajaxChannelList")
	@ResponseBody
	public Object ajaxChannelList(ModelMap modelMap){
		logger.info("{}ajax查询渠道list信息start...");
		Map<String,Object> params=new HashMap<>();
		List<TCollectionChannel> list =tCollectionChannelService.queryList(params);
		logger.info("{}ajax查询渠道list信息end...出参...",list);
		modelMap.put("list", list);
		return setSuccessResponse(modelMap);
	}
	@RequestMapping("/ajaxVideoList")
	@ResponseBody
	public Object ajaxVideoList(ModelMap modelMap){
		logger.info("{}ajax查询视频list信息start...");
		Map<String,Object> params=new HashMap<>();
		List<TVideo> list =tVideoService.queryList(params);
		logger.info("{}ajax查询视频list信息end...出参...",list);
		modelMap.put("list", list);
		return setSuccessResponse(modelMap);
	}
	
	@RequestMapping("/ajaxTagsList")
	@ResponseBody
	public Object ajaxTagsList(ModelMap modelMap,@RequestParam(required=false) Map<String,Object> params){
		logger.info("{}ajax查询标签list信息start...");
		List<TTags> list =tagsService.queryList(params);
		logger.info("{}ajax查询标签list信息end...出参...",list);
		modelMap.put("list", list);
		return setSuccessResponse(modelMap);
	}
	@RequestMapping("/ajaxVipList")
	@ResponseBody
	public Object ajaxVipList(ModelMap modelMap){
		logger.info("{}ajax查询vip list信息start...");
		Map<String,Object> params=new HashMap<>();
		List<Map<String, Object>> list =tVipService.selectListPage(params);
		logger.info("{}ajax查询标签list信息end...出参...",list);
		modelMap.put("list", list);
		return setSuccessResponse(modelMap);
	}
	@RequestMapping("/ajaxLevelList")
	@ResponseBody
	public Object ajaxLevelList(ModelMap modelMap){
		logger.info("{}ajax查询等级list信息start...");
		Map<String,Object> params=new HashMap<>();
		List<TLevel> list =tLevelService.queryList(params);
		logger.info("{}ajax查询等级list信息end...出参...",list);
		modelMap.put("list", list);
		return setSuccessResponse(modelMap);
	}
	@RequestMapping("/ajaxFromCodeList")
	@ResponseBody
	public Object ajaxFromCodeList(ModelMap modelMap){
		logger.info("{}ajax查询渠道包list信息start...");
		Map<String,Object> params=new HashMap<>();
		List<Map<String, Object>> list =tMemberLoginService.queryFromCodeList(params);
		logger.info("{}ajax查询渠道包list信息end...出参...",list);
		modelMap.put("list", list);
		return setSuccessResponse(modelMap);
	}
}