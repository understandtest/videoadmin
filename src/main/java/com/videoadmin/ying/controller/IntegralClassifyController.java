package com.videoadmin.ying.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.utils.PropertiesUtil;
import com.videoadmin.ying.po.IntegralClassify;
import com.videoadmin.ying.service.IntegralClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author hong
 * @Date 19-8-26
 */
@Controller
@RequestMapping("/admin/integralClassify")
public class IntegralClassifyController extends BaseController {

    @Autowired
    private IntegralClassifyService integralClassifyService;


    @RequestMapping("/list")
    public String sysUserList(ModelMap modelMap, @RequestParam(required = false) Map<String,Object> paraMap){
        logger.info("{}获取积分类别list入参:",paraMap);
        Page<?> page = integralClassifyService.selectListPage(paraMap);
        List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
        logger.info("{}获取积分类别list出参:",records);
        modelMap.put("list", records);
        modelMap.put("param", paraMap);
        modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
        return "/systemSetting/integralClassify/list";
    }

    @RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap,Integer id) {

        if(null != id){
            IntegralClassify integralClassify = new IntegralClassify();
            integralClassify.setId(id);

            integralClassify = integralClassifyService.selectOne(integralClassify);

            modelMap.addAttribute("integralClassify", integralClassify);
            modelMap.addAttribute("types", "编辑");

        }else{
            modelMap.addAttribute("types", "添加");
        }

        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
        return "/systemSetting/integralClassify/edit";

    }

    @PostMapping("/save")
    @ResponseBody
    public Object save(ModelMap modelMap,@RequestBody IntegralClassify integralClassify){

        try {
            integralClassifyService.updateById(integralClassify);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return setSuccessResponse(modelMap);

    }

    @RequestMapping("/validate")
    @ResponseBody
    public Object save(){
        return "true";
    }


}
