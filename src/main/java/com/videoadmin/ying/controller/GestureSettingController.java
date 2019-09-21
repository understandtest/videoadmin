package com.videoadmin.ying.controller;

import com.videoadmin.base.BaseController;
import com.videoadmin.ying.po.GestureSetting;
import com.videoadmin.ying.service.GestureSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author hong
 * @Date 19-8-26
 */
@Controller
@RequestMapping("/admin/gestureSetting")
public class GestureSettingController extends BaseController {

    @Autowired
    private GestureSettingService gestureSettingService;

    @GetMapping("/toEdit")
    public String toEdit(ModelMap modelMap){

        //查询手势数据
        GestureSetting gestureSetting = gestureSettingService.findOne(1);

        modelMap.put("gestureSetting",gestureSetting);

        return "systemSetting/gestureSetting/edit";
    }


    @PostMapping("/save")
    @ResponseBody
    public Object save(ModelMap modelMap,GestureSetting gestureSetting){

        gestureSettingService.save(gestureSetting);

        return setSuccessResponse(modelMap);

    }

}
