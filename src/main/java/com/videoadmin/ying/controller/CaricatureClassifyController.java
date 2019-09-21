package com.videoadmin.ying.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.utils.PropertiesUtil;
import com.videoadmin.utils.redis.CacheUtil;
import com.videoadmin.ying.po.CaricatureClassify;
import com.videoadmin.ying.po.TClassify;
import com.videoadmin.ying.service.CaricatureClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author lbh
 * Date 2019-07-25
 */
@Controller
@RequestMapping("/admin/caricatureClassify")
public class CaricatureClassifyController extends BaseController {


    @Autowired
    private CaricatureClassifyService caricatureClassifyService;


    @RequestMapping("/list")
    public String sysUserList(ModelMap modelMap, @RequestParam(required = false) Map<String,Object> paraMap){
        logger.info("{}获取漫画分类list入参:",paraMap);
        Page<?> page = caricatureClassifyService.selectListPage(paraMap);
        List<Map<String,Object>> records = (List<Map<String, Object>>) page.getRecords();
        logger.info("{}获取漫画分类list出参:",records);
        modelMap.put("list", records);
        modelMap.put("param", paraMap);
        modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
        return "/caricatureManager/classify/list";
    }

    @RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        if(DataUtil.isNotEmpty(param)){
            if (param.get("id")!=null && !("").equals(param.get("id").toString())) {
                logger.info("{}查询分类列表 start...{}", param);
                CaricatureClassify caricatureClassify =new CaricatureClassify();
                caricatureClassify.setId(Integer.valueOf(param.get("id").toString()));
                caricatureClassify = caricatureClassifyService.selectOne(caricatureClassify);
                logger.info("{}查询分类列表 end...{}", caricatureClassify);
                modelMap.addAttribute("classify", caricatureClassify);
                modelMap.addAttribute("types", "编辑");
            }else{
                modelMap.addAttribute("types", "添加");
            }
        }
        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
        return "/caricatureManager/classify/edit";

    }


    @RequestMapping(value = "/update")
    @ResponseBody
    public Object updateUser(ModelMap modelMap,@RequestBody CaricatureClassify caricatureClassify) {

        if (caricatureClassify!=null)
        {

            if(null != caricatureClassify.getId()){
                //更新
                //login.setUpdateBy(getCurrUser().getUserId());
                caricatureClassify.setUpdateTime(new Date());
            }else{
                //新增
                caricatureClassify.setCreateTime(new Date());
            }

            //更新分类表
            logger.info("{}更新分类表 start...{}", caricatureClassify);
            caricatureClassifyService.update(caricatureClassify);
            return setSuccessResponse(modelMap,caricatureClassify);
        }
        return setSuccessResponse(modelMap);
    }

    @PostMapping("/validateName")
    @ResponseBody
    public Object validateName(ModelMap modelMap, @RequestParam Map<String, Object> param) {
        logger.info("{}验证分类名称是否存在入参:",param);
        Integer id = caricatureClassifyService.validateName(param);
        logger.info("{}验证分类名称是否存在出参:",id);
        if(id > 0){
            return "false";
        }
        return "true";
    }

    @RequestMapping(value = "/detail")
    public Object detail(ModelMap modelMap, Integer id) {
        if(id != null){
            CaricatureClassify caricatureClassify = new CaricatureClassify();
            caricatureClassify.setId(id);
            caricatureClassify=caricatureClassifyService.selectOne(caricatureClassify);
            modelMap.addAttribute("classify",caricatureClassify);
        }
        return "/caricatureManager/classify/detail";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
        logger.info("删除分类数据，删除主键为：" + ids);
        Map<String,Object> map=new HashMap<String,Object>();
        if(DataUtil.isNotEmpty(ids)){
            String[] strs=ids.split(",");
            for (String string : strs) {
                int id=Integer.valueOf(string);
                caricatureClassifyService.delete(id);
            }
        }

        return setSuccessResponse(modelMap);
    }
}
