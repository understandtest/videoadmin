package com.videoadmin.ying.controller;

import com.videoadmin.base.BaseController;
import com.videoadmin.ying.po.ShortRealmNameSetting;
import com.videoadmin.ying.service.ShortRealmNameSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Author lbh
 * Date 2019-07-24
 */
@Controller
@RequestMapping("/admin/shortRealmNameSetting")
public class ShortRealmNameSettingController extends BaseController {

    @Autowired
    private ShortRealmNameSettingService shortRealmNameSettingService;

    @GetMapping("/toEdit")
    public String toEdit(ModelMap modelMap){

        ShortRealmNameSetting srs = shortRealmNameSettingService.findOne();

        modelMap.put("srs",srs);

        return "/systemSetting/shortRealmNameSetting/edit";
    }

    @PostMapping("/save")
    @ResponseBody
    public Object save(ModelMap modelMap,ShortRealmNameSetting shortRealmNameSetting){
        shortRealmNameSettingService.save(shortRealmNameSetting);
        return setSuccessResponse(modelMap);
    }



}
