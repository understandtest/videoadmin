package com.videoadmin.ying.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.*;
import com.videoadmin.utils.redis.CacheUtil;
import com.videoadmin.ying.po.*;
import com.videoadmin.ying.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.*;


@Controller
@RequestMapping("/admin/video")
public class TVideoController extends BaseController {

    @Autowired
    private TVideoService tVideoService;

    @Autowired
    private TVideoTagsService tVideoTagsService;
    @Autowired
    private TStarService tStarService;
    @Autowired
    private TTagsService tagsService;
    @Autowired
    private TTagTypeService tagTypeService;
    @Autowired
    private TClassifyService tClassifyService;
    @Autowired
    private TCollectionChannelService tCollectionChannelService;
    @Autowired
    private TViewsHistoryService tViewsHistoryService;
    @Autowired
    private TCareHistoryService tCareHistoryService;
    @Autowired
    private TVideoPayService videoPayService;


    @RequestMapping("/pushList")
    public String selectPushList(ModelMap modelMap, @RequestParam(required = false) Map<String, Object> paraMap) throws UnsupportedEncodingException {
        logger.info("{}获取视频信息list入参:", paraMap);
        //处理乱码
        handlerEncodeing(paraMap);
        paraMap.put("isPush", 1);
        Page<?> page = tVideoService.selectListPage(paraMap);
        List<Map<String, Object>> records = (List<Map<String, Object>>) page.getRecords();
        logger.info("{}获取视频信息list出参:", records);
        modelMap.put("list", records);
        modelMap.put("param", paraMap);
        modelMap.put("pagerInfo", PagerUtil.getPageInfoAndShowSelect(page));
        return "/videomanager/pushvideo/list";
    }

    @RequestMapping("/list")
    public String sysUserList(ModelMap modelMap, @RequestParam(required = false) Map<String, Object> paraMap) throws UnsupportedEncodingException {
        logger.info("{}获取视频信息list入参:", paraMap);
        //处理乱码
        handlerEncodeing(paraMap);
        paraMap.put("isPush", 0);
        Page<?> page = tVideoService.selectListPage(paraMap);
        List<Map<String, Object>> records = (List<Map<String, Object>>) page.getRecords();
        logger.info("{}获取视频信息list出参:", records);
        modelMap.put("list", records);
        modelMap.put("param", paraMap);
        modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
        return "/videomanager/video/list";
    }

    private void handlerEncodeing(Map<String, Object> paraMap) throws UnsupportedEncodingException {
        if (paraMap.containsKey("videoName")) {
            String videoName = paraMap.get("videoName").toString();
            videoName = new String(videoName.getBytes("iso8859-1"), "utf-8");
            //重新填入map
            paraMap.put("videoName", videoName);
        }

    }

    @RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required = false) Map<String, Object> param) {
        List taglist = new ArrayList<>();
        String tagIds = JSON.toJSONString(taglist, true);
        modelMap.addAttribute("tagIds", tagIds);
        if (DataUtil.isNotEmpty(param)) {
            if (param.get("id") != null && !("").equals(param.get("id").toString())) {
                logger.info("{}查询视频列表 start...{}", param);
                Map<String, Object> map = tVideoService.getVideoInfo(param);
                logger.info("{}查询视频列表 end...{}", map);
                modelMap.addAttribute("video", map);
        	    /*Map<String, Object> mapp=new HashMap<>();
        	    mapp.put("videoId", param.get("id").toString());*/
                List<TVideoTags> list = tVideoTagsService.selectByVideoId(Integer.parseInt(param.get("id").toString()));

                List<Integer> tagidList = tVideoTagsService.findTagIdsByVideoId(Integer.parseInt(param.get("id").toString()));

                tagIds = JSON.toJSONString(tagidList);
                modelMap.addAttribute("tagIds", tagIds);
                modelMap.addAttribute("types", "编辑");
            } else {

                modelMap.addAttribute("types", "添加");
            }
        }
        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
        return "videomanager/video/edit";

    }

    @RequestMapping(value = "/topushEdit")
    public Object topushEdit(ModelMap modelMap, @RequestParam(required = false) Map<String, Object> param) {
        List taglist = new ArrayList<>();
        String tagIds = JSON.toJSONString(taglist, true);
        modelMap.addAttribute("tagIds", tagIds);
        if (DataUtil.isNotEmpty(param)) {
            if (param.get("id") != null && !("").equals(param.get("id").toString())) {
                logger.info("{}查询视频列表 start...{}", param);
                Map<String, Object> map = tVideoService.getVideoInfo(param);
                logger.info("{}查询视频列表 end...{}", map);
                modelMap.addAttribute("video", map);
                List<Integer> tagidList = tVideoTagsService.findTagIdsByVideoId(Integer.parseInt(param.get("id").toString()));
                tagIds = JSON.toJSONString(tagidList);
                modelMap.addAttribute("tagIds", tagIds);
                modelMap.addAttribute("types", "编辑");
            } else {
                modelMap.addAttribute("types", "添加");
            }
        }
        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
        return "/videomanager/pushvideo/edit";

    }

    @RequestMapping(value = "/detail")
    public Object detail(ModelMap modelMap, HttpServletRequest request) {
        String id = request.getParameter("id");
        if (DataUtil.isNotEmpty(id)) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", id);
            param.put("isPush", 0);
            Map<String, Object> map = tVideoService.getVideoInfo(param);
            param.clear();
            param.put("videoId", id);
            List<TVideoTags> list = tVideoTagsService.queryList(param);
        	 /*String tagIds="";
        	 for (TVideoTags tVideo : list) {
        		 tagIds=tagIds+tVideo.getTagId()+",";
			}
        	*/
            String tagIds = JSON.toJSONString(list, true);
            modelMap.addAttribute("tagIds", tagIds);
            modelMap.addAttribute("video", map);
        }
        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
        return "/videomanager/video/detail";
    }

    @RequestMapping(value = "/pushDetail")
    public Object pushDetail(ModelMap modelMap, HttpServletRequest request) {
        String id = request.getParameter("id");
        if (DataUtil.isNotEmpty(id)) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", id);
            param.put("isPush", 1);
            Map<String, Object> map = tVideoService.getVideoInfo(param);
            param.clear();
            param.put("videoId", id);
            List<TVideoTags> list = tVideoTagsService.queryList(param);
        /*	 String tagIds="";
        	 for (TVideoTags tVideo : list) {
        		 tagIds=tagIds+tVideo.getTagId()+",";
			}*/
            String tagIds = JSON.toJSONString(list, true);
            modelMap.addAttribute("tagIds", tagIds);
            modelMap.addAttribute("video", map);
        }
        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
        return "/videomanager/pushvideo/detail";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
        logger.info("删除视频数据，删除主键为：" + ids);
        Map<String, Object> map = new HashMap<String, Object>();
        if (DataUtil.isNotEmpty(ids)) {
            String[] strs = ids.split(",");
            for (String string : strs) {
                int id = Integer.valueOf(string);
                tVideoService.delete(id);
                //清除历史记录
                Map<String, Object> paramsMap = new HashMap<String, Object>();
                paramsMap.put("video_id", id);
                tViewsHistoryService.deleteByMap(paramsMap);
                //清除关注
                paramsMap.clear();
                paramsMap.put("video_id", id);
                tCareHistoryService.deleteByMap(paramsMap);

                //删除排行记录
                videoPayService.deleteByVideoId(id);
            }
        }
        CacheUtil.getCache().del("indexInfo");
        CacheUtil.getCache().delAll("getFindVideo_*");
        CacheUtil.getCache().delAll("getFindVideo_ex_*");
        CacheUtil.getCache().delAll("viewHistoryList_*");
        CacheUtil.getCache().delAll("careHistoryList_*");
        return setSuccessResponse(modelMap);
    }

    @RequestMapping(value = "/{type}Batch")
    @ResponseBody
    public Object pushOrLower(ModelMap modelMap, @RequestParam String ids, @PathVariable String type) {
        logger.info("上架/下架视频数据，主键为：" + ids);
        if (DataUtil.isNotEmpty(ids)) {
            String[] strs = ids.split(",");
            for (String string : strs) {
                TVideo tVideo = new TVideo();
                int isPush = 0;
                int id = Integer.valueOf(string);
                tVideo.setId(id);
                if (type.equals("push")) {
                    isPush = 1;
                } else {
                    isPush = 0;
                }
                tVideo.setIsPush(isPush);
                tVideo.setUpdateTime(new Date());
                tVideoService.update(tVideo);
                //清除我的浏览记录
                Map<String, Object> paramsMap = new HashMap<String, Object>();
                paramsMap.put("video_id", id);
                tViewsHistoryService.deleteByMap(paramsMap);
                //清除关注
                paramsMap.clear();
                paramsMap.put("video_id", id);
                tCareHistoryService.deleteByMap(paramsMap);
            }
        }
        CacheUtil.getCache().del("indexInfo");
        CacheUtil.getCache().delAll("getFindVideo_*");
        CacheUtil.getCache().delAll("getFindVideo_ex_*");
        CacheUtil.getCache().delAll("viewHistoryList_*");
        CacheUtil.getCache().delAll("careHistoryList_*");
        return setSuccessResponse(modelMap);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Object addOrUpdata(ModelMap modelMap, @RequestParam Map<String, Object> param) {
        tVideoService.addOrUpdata(param);
        CacheUtil.getCache().del("indexInfo");
        CacheUtil.getCache().delAll("getFindVideo_*");
        CacheUtil.getCache().delAll("getFindVideo_ex_*");
        CacheUtil.getCache().delAll("viewHistoryList_*");
        CacheUtil.getCache().delAll("careHistoryList_*");
        return setSuccessResponse(modelMap);
    }

    @RequestMapping(value = "/downloadVideoTemp")
    public void downloadVideoTemp(HttpServletRequest request, HttpServletResponse response) {
        String dirPath = "/WEB-INF/main/temp";
        String path = request.getServletContext().getRealPath(dirPath);
        DownLoadUtil.download(path + "/视频导入模板.xls", response);
    }

    @RequestMapping(value = "/videoImport")
    @ResponseBody
    public Object videoImport(ModelMap modelMap, @RequestParam MultipartFile file) throws Exception {

        if (!file.isEmpty()) {
            Boolean flag = true;
            StringBuilder errorMsg = new StringBuilder();
            errorMsg.append("<span>");
            int errorLine = 1;// 第一列是标题
            int currentLine = 0;// 当前行数
            int successLine = 0;// 成功行数
            List<String[]> excelList = ExcelReaderUtil.excelToArrayList(
                    file.getOriginalFilename(), file.getInputStream(), null);
            if (excelList == null) {
                // 返回错误信息
                flag = false;
                errorMsg.append("要导入的文件没有数据！");
            } else if (excelList.size() == 0) {
                // 继续返回错误信息
                flag = false;
                errorMsg.append("要导入的文件没有数据！");
            } else {
                for (String[] row : excelList) {
                    int m = 0;
                    for (int i = 0; i < row.length; i++) {
                        if (StringUtils.isBlank(row[i])) {
                            m++;
                        }
                    }
                    if (m != row.length && row.length == 11) {
                        // 第0行不做处理
                        if (!"".equals(row) && row != null && errorLine != 1) {
                            // 每行去做处理判断 是否满足数据格式等等 遇到不满足就结束循环 去返回错误信息
                            String videoName = row[0].trim();
                            if (StringUtils.isEmpty(videoName)) {
                                flag = false;
                                errorMsg.append("错误行数:第" + errorLine + "行,错误原因:视频名称没有内容!<br/>");
                            }
	                        
	                        /*String starName = row[1].trim();
	                        if (StringUtils.isEmpty(starName))
	                        {
	                            flag = false;
	                            errorMsg.append("错误行数:第" + errorLine + "行,错误原因:明星名称没有内容!<br/>");
	                        }*/

                            String videoUrl = row[2].trim();
                            if (StringUtils.isEmpty(videoUrl)) {
                                flag = false;
                                errorMsg.append("错误行数:第" + errorLine + "行,错误原因:视频链接地址没有内容!<br/>");
                            }
                            String videoCover = row[3].trim();
                            if (StringUtils.isEmpty(videoCover)) {
                                flag = false;
                                errorMsg.append("错误行数:第" + errorLine + "行,错误原因:封面地址没有内容!<br/>");
                            }

                            String classifyId = row[4].trim();
                            if (StringUtils.isEmpty(classifyId)) {
                                flag = false;
                                errorMsg.append("错误行数:第" + errorLine + "行,错误原因:分类没有内容!<br/>");
                            }
                            String tags = row[6].trim();
                            if (StringUtils.isEmpty(tags)) {
                                flag = false;
                                errorMsg.append("错误行数:第" + errorLine + "行,错误原因:标签没有内容!<br/>");
                            }
	                       
	                       /* String briefContent = row[7].trim();
	                        if (StringUtils.isEmpty(briefContent))
	                        {
	                            flag = false;
	                            errorMsg.append("错误行数:第" + errorLine + "行,错误原因:视频简介没有内容!<br/>");
	                        }*/
                            String pushTime = row[8].trim();
                            if (pushTime.length() > 0) {
                                try {
                                    Date birDate = DateUtil.stringToDate(pushTime, "yyyy-MM-dd");
                                } catch (ParseException e) {
                                    flag = false;
                                    errorMsg.append("错误行数:第" + errorLine + "行,错误原因:上映时间格式不正确!<br/>");
                                    e.printStackTrace();
                                }

                            }
                           /* if(StringUtils.isNotEmpty(row[1].trim())){ 
                            TStar tStar=new TStar();
                            tStar.setName(row[1].trim());
	                        logger.info("根据明星名称查找明星start... {}", tStar);
	                        tStar= tStarService.selectOne(tStar);
	                        logger.info("根据明星名称查找明星 end...{}", tStar);
	                        if(DataUtil.isEmpty(tStar)){
	                       	 flag = false;
	                       	 errorMsg.append("错误行数:第" + errorLine + "行,错误原因:明星名称不存在!<br/>");
	                        }
                            }*/
                            if (StringUtils.isNotEmpty(row[4].trim())) {
                                TClassify tClassify = new TClassify();
                                tClassify.setName(row[4].trim());
                                logger.info("根据分类名称查找分类start... {}", tClassify);
                                tClassify = tClassifyService.selectOne(tClassify);
                                logger.info("根据分类名称查找分类 end...{}", tClassify);
                                if (DataUtil.isEmpty(tClassify)) {
                                    flag = false;
                                    errorMsg.append("错误行数:第" + errorLine + "行,错误原因:分类不存在!<br/>");
                                }
                            }
	                   /*     if(StringUtils.isNotEmpty(row[5].trim())){ 
	                        	String tagstr=row[5].trim();
	                        	String[] tagArry=tagstr.split(",");
	                        int count=0;
	                        	for (String str : tagArry) {
	                        		TTags tTags=new TTags();
	                        		tTags.setName(str);
	                        		logger.info("根据标签名称查找标签start... {}",  tTags);
	                        		tTags=tagsService.selectOne(tTags);
	                        		logger.info("根据标签名称查找标签end...{}", tTags);
	                        		 if(DataUtil.isEmpty(tTags)){
	                        			 count++;
	    		                     }
								}
	                        	 //获取一个标签类型id
		                        Map<String, Object> map = new HashMap<>();
		                        Page<Map<String, Object>> page= tagTypeService.selectListPage(map);
		                        List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
		                        if(records.size()>0 ){
		                        	if(count>0){
	   		                       	 flag = false;
	   		                       	 errorMsg.append("错误行数:第" + errorLine + "行,错误原因:有标签不存在!<br/>");
	   		                     }
		                        }else{
		                        	if(count>0){
		                        		flag = false;
				                       	 errorMsg.append("错误行数:第" + errorLine + "行,错误原因:标签类型不存在，无法进行标签添加!<br/>");
		   		                     }
		                        }
	                        	
	                       }*/
                        }
                        errorLine++;
                    }
                }
            }
            if (flag) {
                // 如果没有一个错误 则 flag还是true
                for (String[] row : excelList) {
                    int n = 0;
                    for (int i = 0; i < row.length; i++) {
                        if (StringUtils.isBlank(row[i])) {
                            n++;
                        }
                    }
                    if (n != row.length && row.length == 11) {
                        if (currentLine != 0) {
                            Date date = new Date();
	                      /*  //获取一个标签类型id
	                        Map<String, Object> map = new HashMap<>();
	                        Page<Map<String, Object>> page= tagTypeService.selectListPage(map);
	                        List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
	                        Integer tagTypeId=0;
	                        if(records.size()>0 ){
	                        	tagTypeId=Integer.valueOf(records.get(0).get("id").toString());
	                        }*/
                            TVideo tVideo = new TVideo();
                            tVideo.setIsPush(0);
                            if (StringUtils.isNotEmpty(row[0].trim())) {
                                tVideo.setVideoName(row[0].trim());
                            }
                            if (StringUtils.isNotEmpty(row[2].trim())) {
                                tVideo.setVideoUrl(row[2].trim());
                            }
                            if (StringUtils.isNotEmpty(row[3].trim())) {
                                tVideo.setVideoCover(row[3].trim());
                            }
                            if (StringUtils.isNotEmpty(row[9].trim())) {
                                tVideo.setSourceId(row[9].trim());
                            }
                            if (StringUtils.isNotEmpty(row[10].trim())) {
                                tVideo.setSourceUrl(row[10].trim());
                            }
                            String briefContent = row[7].trim();
                            if (StringUtils.isNotEmpty(briefContent)) {
                                tVideo.setBriefContent(briefContent);
                            } else {
                                tVideo.setBriefContent("暂无简介");
                            }

                            String pushTime = row[8].trim();
                            if (pushTime.length() > 0) {
                                Date birDate = DateUtil.stringToDate(pushTime, "yyyy-MM-dd");
                                tVideo.setPushTime(birDate);
                            }
                            if (StringUtils.isNotEmpty(row[1].trim())) {
                                TStar tStar = new TStar();
                                tStar.setName(row[1].trim());
                                logger.info("根据明星名称查找明星start... {}", tStar);
                                TStar star = tStarService.selectOne(tStar);
                                logger.info("根据明星名称查找明星 end...{}", star);
                                if (star != null) {
                                    tVideo.setStarId(star.getId());
                                } else {
                                    //新增时，添加默认图片
                                    tStar.setPicType(2);
                                    Map<String, Object> paraMap = new HashMap<>();
                                    Page<?> page = tStarService.selectListPage(paraMap);
                                    List<Map<String, Object>> records = (List<Map<String, Object>>) page.getRecords();
                                    if (records != null && records.size() > 0) {
                                        tStar.setHeadpic(records.get(0).get("headpic").toString());
                                    }
                                    tStar = tStarService.update(tStar);
                                    tVideo.setStarId(tStar.getId());
                                }
                            } else {
                                TStar tStar = new TStar();
                                tStar.setName("其他");
                                TStar star1 = tStarService.selectOne(tStar);
                                if (star1 != null) {
                                    tVideo.setStarId(star1.getId());
                                } else {
                                    //新增时，添加默认图片
                                    tStar.setPicType(2);
                                    Map<String, Object> paraMap = new HashMap<>();
                                    Page<?> page = tStarService.selectListPage(paraMap);
                                    List<Map<String, Object>> records = (List<Map<String, Object>>) page.getRecords();
                                    if (records != null && records.size() > 0) {
                                        String headpic = records.get(0).get("headpic").toString();
                                        tStar.setHeadpic(headpic);
                                    }
                                    tStar = tStarService.update(tStar);
                                    tVideo.setStarId(tStar.getId());
                                }
                            }
                            if (StringUtils.isNotEmpty(row[4].trim())) {
                                TClassify tClassify = new TClassify();
                                tClassify.setName(row[4].trim());
                                logger.info("根据分类名称查找分类start... {}", tClassify);
                                tClassify = tClassifyService.selectOne(tClassify);
                                logger.info("根据分类名称查找分类 end...{}", tClassify);
                                if (tClassify != null) {
                                    tVideo.setClassifyId(tClassify.getId());
                                }
                            }
                            Integer tagTypeId = 0;
//	        	                     if(StringUtils.isNotEmpty(row[5].trim())){ 
//	        	                    	 String tagType=row[5].trim();
//	        	                    	 TTagType tTagType=new TTagType();
//	        	                    	 tTagType.setName(tagType);
//	        	                    	 TTagType tags=tagTypeService.selectOne(tTagType);
//	        	                    	 if(DataUtil.isNotEmpty(tags)){
//	        	                    		 tagTypeId=tags.getId();
//	        	                    	 }else{
//				                            		tTagType=tagTypeService.update(tTagType);
//				                            		tagTypeId=tTagType.getId();
//	        	                    	 }
//	        	                     }else{
//	        	                    	 TTagType tTagType=new TTagType();
//        	                    		 tTagType.setName("其他");
//        	                    		 TTagType tagType2=tagTypeService.selectOne(tTagType);
//        	                    		 if(tagType2!=null){
//        	                    			 tagTypeId=tagType2.getId();
//			                            	}else{
//			                            		tTagType=tagTypeService.update(tTagType);
//			                            		tagTypeId=tTagType.getId();
//			                            	}
//        	                    	 }
                            if (StringUtils.isNotEmpty(row[5].trim())) {
                                String channelName = row[5].trim();
                                TCollectionChannel channel = new TCollectionChannel();
                                channel.setChannelName(channelName);
                                channel = tCollectionChannelService.selectOne(channel);
                                if (channel == null || "".equals(channel)) {
                                    channel = new TCollectionChannel();
                                    channel.setChannelName(channelName);
                                    channel = tCollectionChannelService.update(channel);
                                }
                                tVideo.setChannelId(channel.getId());
                            }
                            tVideoService.update(tVideo);
                            TTagType tTagType = new TTagType();
                            tTagType.setName("其他");
                            TTagType tagType2 = tagTypeService.selectOne(tTagType);
                            if (tagType2 != null) {
                                tagTypeId = tagType2.getId();
                            } else {
                                tTagType = new TTagType();
                                tTagType.setName("其他");
                                tTagType = tagTypeService.update(tTagType);
                                tagTypeId = tTagType.getId();
                            }
                            if (StringUtils.isNotEmpty(row[6].trim())) {
                                String tagstr = row[6].trim();
                                String[] tagArry = tagstr.split(",");
                                for (String str : tagArry) {
                                    TTags tTags = new TTags();
                                    tTags.setName(str);
                                    tTags = tagsService.selectOne(tTags);
                                    if (tTags == null || "".equals(tTags)) {
                                        tTags = new TTags();
                                        tTags.setName(str);
                                        tTags.setTagTypeId(tagTypeId);
                                        tTags.setPicType(2);
                                        Map<String, Object> paraMap = new HashMap<>();
                                        Page<?> page = tagsService.selectListPage(paraMap);
                                        List<Map<String, Object>> records = (List<Map<String, Object>>) page.getRecords();
                                        if (records != null && records.size() > 0) {
                                            Object picUrl = records.get(0).get("picUrl");
                                            if (picUrl != null) {
                                                tTags.setPicUrl(picUrl + "");
                                            }
                                        }
                                        tTags = tagsService.update(tTags);
                                    }
                                    TVideoTags tVideoTags = new TVideoTags();
                                    tVideoTags.setVideoId(tVideo.getId());
                                    tVideoTags.setTagId(tTags.getId());
                                    tVideoTagsService.update(tVideoTags);
                                }
                            }
                            successLine++;
                        }
                        currentLine++;
                    }
                }
                return setSuccessResponse(modelMap, HttpCode.OK, "本次共需要导入" + (currentLine - 1)
                        + "条数据,成功导入" + successLine
                        + "条!");
            } else {
                errorMsg.append("</span>");
                return setSuccessResponse(modelMap, HttpCode.BAD_REQUEST, errorMsg);
            }
        } else {
            return setSuccessResponse(modelMap, HttpCode.BAD_REQUEST, "请选择要导入的文件!");
        }
    }
}