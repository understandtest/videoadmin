package com.videoadmin.ying.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.videoadmin.base.BaseController;
import com.videoadmin.ying.service.TQqService;


@Controller
@RequestMapping("/admin/qq")
public class TQqController extends BaseController {

    @Autowired
    private TQqService tQqService;


    @RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap) {
        Map<String, Object> param = new HashMap<>();
        logger.info("{}查询QQ管理列表 start...{}", param);
        List<Map<String, Object>> list = tQqService.selectListPage(param);
        logger.info("{}查询QQ管理列表 end...{}", list);
        if (list != null) {
            Map<String, Object> tQq = list.get(0);
            modelMap.addAttribute("qq", tQq);
            modelMap.addAttribute("types", "编辑");
        } else {
            modelMap.addAttribute("types", "添加");
        }
        return "/qq/edit";

    }

    @RequestMapping(value = "/addOrUpdata")
    @ResponseBody
    public Object addOrUpdata(ModelMap modelMap, @RequestParam Map<String, Object> param) {
        tQqService.addOrUpdata(param);

        return setSuccessResponse(modelMap);
    }


}