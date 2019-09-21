package com.videoadmin.ying.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.utils.PropertiesUtil;
import com.videoadmin.utils.redis.CacheUtil;
import com.videoadmin.ying.po.IntegralVip;
import com.videoadmin.ying.service.IntegralVipService;
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
@RequestMapping("/admin/integralVip")
public class IntegralVipController extends BaseController {

    @Autowired
    private IntegralVipService integralVipService;

    @GetMapping("/list")
    public String list(ModelMap modelMap, @RequestParam(required = false) Map<String,Object> paraMap){
        logger.info("{}获取积分类别list入参:",paraMap);
        Page<?> page = integralVipService.selectListPage(paraMap);
        List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
        logger.info("{}获取积分类别list出参:",records);
        modelMap.put("list", records);
        modelMap.put("param", paraMap);
        modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
        return "/systemSetting/integralVip/list";
    }

    @RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap,Integer id) {

        if(null != id){

            IntegralVip integralVip = integralVipService.findOne(id);

            modelMap.addAttribute("integralVip", integralVip);
            modelMap.addAttribute("types", "编辑");

        }else{
            modelMap.addAttribute("types", "添加");
        }

        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
        return "/systemSetting/integralVip/edit";

    }

    @PostMapping("/save")
    @ResponseBody
    public Object save(ModelMap modelMap,@RequestBody IntegralVip integralVip){

        try {
            integralVipService.save(integralVip);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return setSuccessResponse(modelMap);

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
                integralVipService.delete(id);
            }
        }
        CacheUtil.getCache().del("indexInfo");
        return setSuccessResponse(modelMap);
    }

    @RequestMapping("/validate")
    @ResponseBody
    public Object save(){
        return "true";
    }


}
