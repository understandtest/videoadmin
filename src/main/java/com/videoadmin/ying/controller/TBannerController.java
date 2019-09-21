package com.videoadmin.ying.controller;

import java.util.Date;
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
import com.videoadmin.utils.PropertiesUtil;
import com.videoadmin.utils.redis.CacheUtil;
import com.videoadmin.ying.po.TBanner;
import com.videoadmin.ying.po.TVideo;
import com.videoadmin.ying.service.TBannerService;
import com.videoadmin.ying.service.TVideoService;


@Controller
@RequestMapping("/admin/banner")
public class TBannerController extends BaseController {

    @Autowired
    private TBannerService tBannerService;
    @Autowired
    private TVideoService tVideoService;

    @RequestMapping("/list")
    public String sysUserList(ModelMap modelMap, @RequestParam(required = false) Map<String, Object> paraMap) {
        logger.info("{}获取广告信息list入参:", paraMap);
        Page<?> page = tBannerService.selectListPage(paraMap);
        List<Map<String, Object>> records = (List<Map<String, Object>>) page.getRecords();
        logger.info("{}获取广告信息list出参:", records);
        if (records != null) {
            for (Map<String, Object> map : records) {
                if (map.get("linkType") != null && !"".equals(map.get("linkType"))) {
                    int linkType = Integer.valueOf(map.get("linkType").toString());
                    if (linkType == 2) {
                        if (map.get("linkUrl") != null && !("").equals(map.get("linkUrl"))) {
                            int id = Integer.valueOf(map.get("linkUrl").toString());
                            TVideo tVideo = new TVideo();
                            tVideo.setId(id);
                            tVideo = tVideoService.selectOne(tVideo);
                            if (tVideo != null) {
                                map.put("linkUrl", tVideo.getVideoUrl());
                            }

                        }
                    }
                }
            }
        }
        modelMap.put("list", records);
        modelMap.put("param", paraMap);
        modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
        return "/systemSetting/banner/list";
    }

    @RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required = false) Map<String, Object> param) {
        if (DataUtil.isNotEmpty(param)) {
            if (param.get("id") != null && !("").equals(param.get("id").toString())) {
                logger.info("{}查询广告管理列表 start...{}", param);
                TBanner tBanner = new TBanner();
                tBanner.setId(Integer.valueOf(param.get("id").toString()));
                tBanner = tBannerService.selectOne(tBanner);
                logger.info("{}查询广告管理列表 end...{}", tBanner);
                modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
                modelMap.addAttribute("banner", tBanner);
                modelMap.addAttribute("types", "编辑");
            } else {
                modelMap.addAttribute("types", "添加");
            }
        }
        return "/systemSetting/banner/edit";
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
        logger.info("删除广告数据，删除主键为：" + ids);
        if (DataUtil.isNotEmpty(ids)) {
            String[] strs = ids.split(",");
            for (String string : strs) {
                int id = Integer.valueOf(string);
                //删除广告表
                tBannerService.delete(id);
            }
        }
        CacheUtil.getCache().del("indexInfo");
        return setSuccessResponse(modelMap);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Object addOrUpdata(ModelMap modelMap, @RequestParam Map<String, Object> param) {
        tBannerService.addOrUpdata(param);
        CacheUtil.getCache().del("indexInfo");
        return setSuccessResponse(modelMap);
    }

    @RequestMapping(value = "/detail")
    public Object detail(ModelMap modelMap, HttpServletRequest request) {
        String id = request.getParameter("id");
        if (DataUtil.isNotEmpty(id)) {
            TBanner tBanner = new TBanner();
            tBanner.setId(Integer.valueOf(Integer.valueOf(id)));
            tBanner = tBannerService.selectOne(tBanner);
            modelMap.addAttribute("banner", tBanner);
            modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
        }
        return "/systemSetting/banner/detail";
    }

    @RequestMapping(value = "/isShowBatch")
    @ResponseBody
    public Object isShowBatch(ModelMap modelMap, @RequestParam Map<String, Object> param) {
        logger.info("有效/无效数据，主键为：" + param);
        String isShow = "";
        if (param.get("isShow") != null && !("").equals(param.get("isShow").toString())) {
            isShow = param.get("isShow").toString();
        }
        if (param.get("ids") != null && !("").equals(param.get("ids").toString())) {
            String ids = param.get("ids").toString();
            String[] strs = ids.split(",");
            for (String string : strs) {
                TBanner tBanner = new TBanner();
                Integer id = Integer.valueOf(string);
                tBanner.setId(id);
                if (!("").equals(isShow)) {
                    tBanner.setIsShow(Integer.valueOf(isShow));
                }
                tBanner.setUpdateTime(new Date());
                tBannerService.update(tBanner);
            }
        }

        return setSuccessResponse(modelMap);
    }

}