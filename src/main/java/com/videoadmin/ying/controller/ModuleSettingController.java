package com.videoadmin.ying.controller;

import com.videoadmin.base.BaseController;
import com.videoadmin.ying.po.ModuleSetting;
import com.videoadmin.ying.service.ModuleSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author hong
 * @Date 19-9-10
 */
@Controller
@RequestMapping("/admin/moduleSetting")
public class ModuleSettingController extends BaseController {

    @Autowired
    private ModuleSettingService moduleSettingService;


    @GetMapping("/toEdit")
    public String toEdit(ModelMap modelMap){

        // 查询后台开启状态
        Map<String, Object> starModuleSetting = moduleSettingService.findOne(1);
        // 查询后台漫画开启状态
        Map<String, Object> caricatureModuleSetting = moduleSettingService.findOne(2);

        modelMap.put("starModuleSetting",starModuleSetting);
        modelMap.put("caricatureModuleSetting",caricatureModuleSetting);

        return "/systemSetting/moduleSetting/edit";
    }


    @PostMapping("/save")
    @ResponseBody
    public Object save(ModelMap modelMap, @RequestBody List<ModuleSetting> moduleSettingList){
        moduleSettingService.update(moduleSettingList);
        return setSuccessResponse(modelMap);
    }


}
