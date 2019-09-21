package com.videoadmin.ying.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.utils.PropertiesUtil;
import com.videoadmin.utils.StringUtils;
import com.videoadmin.ying.po.*;
import com.videoadmin.ying.service.CaricatureBookcaseService;
import com.videoadmin.ying.service.CaricatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Author lbh
 * Date 2019-07-25
 */
@Controller
@RequestMapping("/admin/caricature")
public class CaricatureController extends BaseController {


    @Autowired
    private CaricatureService caricatureService;

    @Autowired
    private CaricatureBookcaseService caricatureBookcaseService;


    /**
     * 查询未上架漫画
     * @param modelMap
     * @param paraMap
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/list/{isPush}")
    public String sysUserList(ModelMap modelMap,@PathVariable Integer isPush, @RequestParam(required = false) Map<String,Object> paraMap) {
        logger.info("{}获取漫画信息list入参:",paraMap);

        if(!(isPush == 0 || isPush == 1)){
            isPush = 0; //默认查询未上架漫画
        }

        paraMap.put("isPush", isPush);

        Page<?> page = caricatureService.selectListPage(paraMap);
        List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
        logger.info("{}获取漫画信息list出参:",records);
        modelMap.put("list", records);
        modelMap.put("param", paraMap);
        modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));

        return isPush == 0?"/caricatureManager/caricature/list":"/caricatureManager/pushCaricature/list";
    }





    @RequestMapping(value = "/{type}Batch")
    @ResponseBody
    public Object pushOrLower(ModelMap modelMap, @RequestParam String ids,@PathVariable String type) {
        logger.info("上架/下架视频数据，主键为：" + ids);
        if(DataUtil.isNotEmpty(ids)){
            String[] strs=ids.split(",");
            for (String string : strs) {
                Caricature caricature=new Caricature();
                int isPush=0;
                int id=Integer.valueOf(string);
                caricature.setId(id);

                if(type.equals("push")){
                    isPush=1;
                }else{
                    isPush=0;
                    // todo 清理我的书架
                    caricatureBookcaseService.delByCaricatureId(id);
                }
                caricature.setIsPush(isPush + "");
                caricature.setUpdateTime(new Date());
                caricatureService.updateById(caricature);


            }
        }
        return setSuccessResponse(modelMap);
    }



    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
        try {
            caricatureService.batchDelete(ids);
            return setSuccessResponse(modelMap);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("resCode",0);
            modelMap.put("resMsg","系统出错了");
            return setSuccessResponse(modelMap);
        }

    }


    @RequestMapping(value = "/toEdit/{isPush}")
    public Object toUpdate(ModelMap modelMap, Integer id,String type,@PathVariable Integer isPush) {

        if(!(isPush == 0 || isPush == 1)){
            isPush = 0; //默认查询未上架漫画
        }

        String caricaturePath = isPush == 0?"caricature":"pushCaricature"; //根据是否上架，判断动漫跳转地址

        String location = "caricatureManager/"+caricaturePath+"/edit";

        if (id !=null) {
            Caricature caricature = new Caricature();
            caricature.setId(id);
            caricature = caricatureService.selectOne(caricature);
            modelMap.addAttribute("caricature", caricature);
            modelMap.addAttribute("types", "编辑");
        }else{
            modelMap.addAttribute("types", "添加");
        }
        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));

        if(StringUtils.isNotEmpty(type) && "detail".equals(type)){
            location =  "caricatureManager/"+caricaturePath+"/detail";
        }

        return location;

    }


    @RequestMapping(value = "/update")
    @ResponseBody
    public Object addOrUpdate(ModelMap modelMap,@RequestBody Caricature caricature) {
        caricatureService.addOrUpdate(caricature);
        return setSuccessResponse(modelMap);
    }

    @PostMapping("/validateCaricatureName")
    @ResponseBody
    public Object validateCaricatureName(@RequestParam Map<String, Object> param) {
        logger.info("{}验证分类名称是否存在入参:",param);
        Integer id = caricatureService.validateCaricatureName(param);
        logger.info("{}验证分类名称是否存在出参:",id);
        if(id > 0){
            return "false";
        }
        return "true";
    }

}
