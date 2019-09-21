package com.videoadmin.ying.controller;

import com.videoadmin.base.BaseController;
import com.videoadmin.ying.po.AcGroup;
import com.videoadmin.ying.service.AcGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author lbh
 * @Date 2019/8/7
 */
@Controller
@RequestMapping("/admin/acGroup")
public class AcGroupController extends BaseController {

    @Autowired
    private AcGroupService acGroupService;


    @RequestMapping(value = "/toEdit")
    public Object toEdit(ModelMap modelMap) {

        AcGroup acGroup = acGroupService.findOne(1);
        modelMap.addAttribute("acGroup", acGroup);
        modelMap.addAttribute("types", "编辑");

        return "/acGroup/edit";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(ModelMap modelMap,AcGroup acGroup) {
        acGroupService.save(acGroup);
        return setSuccessResponse(modelMap);
    }


}
