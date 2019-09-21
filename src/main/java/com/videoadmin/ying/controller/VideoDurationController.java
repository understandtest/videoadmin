package com.videoadmin.ying.controller;

import com.videoadmin.base.BaseController;
import com.videoadmin.ying.po.VideoDuration;
import com.videoadmin.ying.service.VideoDurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author lbh
 * @Date 2019/6/14
 * 视频时长控制器
 */
@Controller
@RequestMapping(value = "/admin/videoDuration")
public class VideoDurationController extends BaseController {

    @Autowired
    private VideoDurationService videoDurationService;

    @GetMapping("/getVideoDuration")
    public String getVideoDuration(ModelMap modelMap){

        VideoDuration videoDuration = videoDurationService.getVideoDuration();

        modelMap.put("videoDuration",videoDuration);

        return "/videomanager/duration/edit";
    }

    @PostMapping("save")
    @ResponseBody
    public Object save(ModelMap modelMap,VideoDuration VideoDuration){

        videoDurationService.updateVideoDuration(VideoDuration);

        return setSuccessResponse(modelMap);

    }




}
