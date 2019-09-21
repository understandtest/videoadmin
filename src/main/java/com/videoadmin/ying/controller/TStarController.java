package com.videoadmin.ying.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.*;
import com.videoadmin.utils.redis.CacheUtil;
import com.videoadmin.ying.po.TStar;
import com.videoadmin.ying.service.MemberStarService;
import com.videoadmin.ying.service.TStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin/star")
public class TStarController extends BaseController{
	
	@Autowired
	private TStarService tStarService;

	@Autowired
	private MemberStarService memberStarService;
	
	@RequestMapping("/list")
	public String sysUserList(ModelMap modelMap,@RequestParam(required = false) Map<String,Object> paraMap){
		logger.info("{}获取明星信息list入参:",paraMap);
		Page<?> page = tStarService.selectListPage(paraMap);
		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		logger.info("{}获取明星信息list出参:",records);
		modelMap.put("list", records);
		modelMap.put("param", paraMap);
		modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
		return "/videomanager/starinfo/list";
	}
	@RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        if(DataUtil.isNotEmpty(param)){
        	if (param.get("id")!=null && !("").equals(param.get("id").toString())) {
        	    logger.info("{}查询明星管理列表 start...{}", param);
        	    TStar tStar=new TStar();
            	tStar.setId(Integer.valueOf(param.get("id").toString()));
            	tStar=tStarService.selectOne(tStar);
        	    logger.info("{}查询明星管理列表 end...{}", tStar);
        	    modelMap.addAttribute("star", tStar);
        	    modelMap.addAttribute("types", "编辑");
            }else{
            	 modelMap.addAttribute("types", "添加");
            }
        }
        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
 	return "/videomanager/starinfo/edit";    
}
 
    @RequestMapping(value = "/detail")
    public Object detail(ModelMap modelMap,HttpServletRequest request) {
        String id = request.getParameter("id");
        if(DataUtil.isNotEmpty(id)){
        	TStar tStar=new TStar();
        	tStar.setId(Integer.valueOf(id));
        	tStar=tStarService.selectOne(tStar);
        	modelMap.addAttribute("star",tStar);
        }
        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
        return "/videomanager/starinfo/detail";    
    }
 
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
			logger.info("删除明星数据，删除主键为：" + ids);
			if(DataUtil.isNotEmpty(ids)){
				String[] strs=ids.split(",");
				for (String string : strs) {
					int id=Integer.valueOf(string);
					//删除明星表
					tStarService.delete(id);

					// 删除用户收藏记录
					memberStarService.delMemberCollectByStarId(id);
				}
			}
			
				return setSuccessResponse(modelMap);  
	}
	@RequestMapping(value = "/addOrUpdata")
	@ResponseBody
	public Object addOrUpdata(ModelMap modelMap,@RequestParam Map<String, Object> param) {
		tStarService.addOrUpdata(param);
		CacheUtil.getCache().del("indexInfo");
		return setSuccessResponse(modelMap);
	}
	@PostMapping("/validateName")
    @ResponseBody
    public Object validateName(ModelMap modelMap, @RequestParam Map<String, Object> param) {
    	logger.info("{}验证明星名称是否存在入参:",param);
		//有id则直接吸怪
		if(null != param.get("id") && !"".equals(param.get("id"))){
				return true;
		}

    	Integer id = tStarService.validateName(param);

		logger.info("{}验证明星名称是否存在出参:",id);
		if(id > 0){
			return "false";
		}
		return "true";
    }
	@RequestMapping(value =  "/downloadStarTemp")
    public void downloadStarTemp(HttpServletRequest request, HttpServletResponse response)
    {
        String dirPath = "/WEB-INF/main/temp";
        String path = request.getServletContext().getRealPath(dirPath);
        DownLoadUtil.download(path + "/明星导入模板.xls", response);
    }
	
    @RequestMapping(value= "/starImport")
    @ResponseBody
    public Object starImport(ModelMap modelMap, @RequestParam MultipartFile file) throws Exception {

    	if (!file.isEmpty())
        {
            Boolean flag = true;
            StringBuilder errorMsg = new StringBuilder();
            errorMsg.append("<span>");
            int errorLine = 1;// 第一列是标题
            int currentLine = 0;// 当前行数
            int successLine = 0;// 成功行数
            List<String[]> excelList = ExcelReaderUtil.excelToArrayList(
                file.getOriginalFilename(), file.getInputStream(), null);
            if (excelList == null)
            {
                // 返回错误信息
                flag = false;
                errorMsg.append("要导入的文件没有数据！");
            }
            else if (excelList.size() == 0)
            {
                // 继续返回错误信息
                flag = false;
                errorMsg.append("要导入的文件没有数据！");
            }
            else
            {
                for (String[] row : excelList)
                {
                	int m = 0;
                    for (int i = 0; i < row.length; i++ )
                    {
                        if (StringUtils.isBlank(row[i]))
                        {
                            m++;
                        }
                    }
                    if(m!=row.length){
	                    // 第0行不做处理
	                    if (!"".equals(row)&&row!=null&&errorLine != 1)
	                    {
	                        // 每行去做处理判断 是否满足数据格式等等 遇到不满足就结束循环 去返回错误信息
	                        String starName = row[0].trim();
	                        if (StringUtils.isEmpty(starName))
	                        {
	                            flag = false;
	                            errorMsg.append("错误行数:第" + errorLine + "行,错误原因:明星名称没有内容!<br/>");
	                        }
	                        
	                        /*String starName = row[1].trim();
	                        if (StringUtils.isEmpty(starName))
	                        {
	                            flag = false;
	                            errorMsg.append("错误行数:第" + errorLine + "行,错误原因:明星名称没有内容!<br/>");
	                        }*/
	                        
	                        
	                    } 
	                    errorLine++ ;
                    }
                }
            }
            if (flag)
            {
                // 如果没有一个错误 则 flag还是true
                for (String[] row : excelList)
                {
                	int n = 0;
                    for (int i = 0; i < row.length; i++ )
                    {
                        if (StringUtils.isBlank(row[i]))
                        {
                            n++;
                        }
                    }
                	if(n!=row.length){
	                    if (currentLine != 0)
	                    {
	                        Date date = new Date();
	                                TStar tStar=new TStar();
	                                if(StringUtils.isNotEmpty(row[0].trim())){ 
	                                    TStar tStar1=new TStar();
	                                    tStar1.setName(row[0].trim());
	        	                        logger.info("根据明星名称查找明星start... {}", tStar1);
	        	                        TStar star= tStarService.selectOne(tStar1);
	        	                        logger.info("根据明星名称查找明星 end...{}", star);
	        	                        if(star!=null){
	        	                        	tStar.setId(star.getId());
			                            }else{
			                            	tStar.setName(row[0].trim());
			                            	tStar.setPicType(2);
			                            }
	                                 }
	                                if (StringUtils.isNotEmpty(row[1].trim()))
	                                {
	                                	tStar.setHeadpic(row[1].trim());
	                                }else{
	                                	Map<String,Object> paraMap=new HashMap<>();
	                                	Page<?> page = tStarService.selectListPage(paraMap);
	                            		List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
	                            		if(records.size()>0) {
	                            			String headpic=records.get(0).get("headpic").toString();
	                            			tStar.setHeadpic(headpic);
	                            		}
	                                }
	        	                     tStarService.update(tStar); 
	        	                      
	                                
	                                successLine++ ;
	                    }
	                    currentLine++ ;
                   }
                }
                return setSuccessResponse(modelMap, HttpCode.OK, "本次共需要导入" + (currentLine - 1)
                                                                 + "条数据,成功导入" + successLine
                                                                 + "条!");
            }
            else
            {
                errorMsg.append("</span>");
                return setSuccessResponse(modelMap, HttpCode.BAD_REQUEST, errorMsg);
            }
        }
        else
        {
            return setSuccessResponse(modelMap, HttpCode.BAD_REQUEST, "请选择要导入的文件!");
        }
    }
}