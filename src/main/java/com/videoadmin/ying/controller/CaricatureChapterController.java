package com.videoadmin.ying.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.PagerUtil;
import com.videoadmin.utils.PropertiesUtil;
import com.videoadmin.utils.StringUtils;
import com.videoadmin.ying.po.CaricatureChapter;
import com.videoadmin.ying.po.CaricatureChapterExt;
import com.videoadmin.ying.po.caricatureChapterImg;
import com.videoadmin.ying.service.CaricatureChapterImgService;
import com.videoadmin.ying.service.CaricatureChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author lbh
 * Date 2019-07-26
 * 漫画章节控制器
 */
@Controller
@RequestMapping("/admin/caricatureChapter")
public class CaricatureChapterController extends BaseController {

    @Autowired
    private CaricatureChapterService caricatureChapterService;

    @Autowired
    private CaricatureChapterImgService caricatureChapterImgService;

    @RequestMapping("/list")
    public String sysUserList(ModelMap modelMap, @RequestParam(required = false) Map<String, Object> paraMap) {
        logger.info("{}获取分类list入参:", paraMap);
        //排序规则
        if (null == paraMap.get("sortType")) {
            paraMap.put("orderType", "asc"); //默认为升序
        } else {
            Object sortType = paraMap.get("sortType");
            if (null != sortType && "".equals(sortType.toString())) {
                String sortTypeStr = sortType.toString();
                String orderType = sortTypeStr.equals("0") ? "asc" : "desc";
                paraMap.put("orderType", orderType);
            }
        }


        Page<Map<String, Object>> page = caricatureChapterService.selectListPage(paraMap);
        List<Map<String, Object>> records = (List<Map<String, Object>>) page.getRecords();
        logger.info("{}获取分类list出参:", records);
        modelMap.put("list", records);
        modelMap.put("param", paraMap);
        modelMap.put("pagerInfo", PagerUtil.getPageInfo(page));
        return "/caricatureManager/caricatureChapter/list";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delByIds(ModelMap modelMap, @RequestParam String ids) {

        try {
            caricatureChapterService.batchDelByIds(ids);
            return setSuccessResponse(modelMap);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("resCode",0);
            modelMap.put("resMsg","系统出错了");
            return setSuccessResponse(modelMap);
        }

    }

    @RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        if(DataUtil.isNotEmpty(param)){
            if (param.get("id")!=null && !("").equals(param.get("id").toString())) {
                logger.info("{}查询分类列表 start...{}", param);
                CaricatureChapter caricatureChapter = new CaricatureChapter();
                Integer id = Integer.valueOf(param.get("id").toString());
                caricatureChapter.setId(id);
                caricatureChapter = caricatureChapterService.selectOne(caricatureChapter);

                //查询章节关联的图片
                List<caricatureChapterImg> caricatureChapterImgs = caricatureChapterImgService.findListByChapterId(id);

                modelMap.addAttribute("caricatureChapter", caricatureChapter);
                modelMap.addAttribute("caricatureChapterImgs", caricatureChapterImgs);

                modelMap.addAttribute("types", "编辑");
            }else{
                modelMap.addAttribute("types", "添加");
            }
        }
        modelMap.put("param", param);
//        modelMap.put("prefix", PropertiesUtil.getString("caricature.remote.file.uri.prefix"));
        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));

        String type = (String)param.get("type");

        if(StringUtils.isNotEmpty(type) && type.equals("detail")){
            return "/caricatureManager/caricatureChapter/detail";
        }

        return "/caricatureManager/caricatureChapter/edit";



    }



    @RequestMapping(value = "/update")
    @ResponseBody
    public Object updateCaricatureChapter(ModelMap modelMap,@RequestBody CaricatureChapterExt caricatureChapterExt) {

        try {
            if (caricatureChapterExt!=null)
            {

                if(null != caricatureChapterExt.getId()){
                    //更新
                    caricatureChapterExt.setUpdateTime(new Date());
                }else{
                    //新增

                    caricatureChapterExt.setCreateTime(new Date());
                }

                //更新分类表
                logger.info("{}更新分类表 start...{}", caricatureChapterExt);

                CaricatureChapter caricatureChapter = caricatureChapterService.update(caricatureChapterExt);

                //获取更新后的id
                Integer caricatureId = caricatureChapter.getId();

                //根据章节id删除图片
                caricatureChapterImgService.delByCaricatureId(caricatureId);

                //批量插入图片
                caricatureChapterImgService.batchAdd(caricatureId,caricatureChapterExt.getCaricatureChapterImgs());

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return setSuccessResponse(modelMap);



    }


    @PostMapping("/validateCaricatureChapterName")
    @ResponseBody
    public Object validateCaricatureChapterName(ModelMap modelMap, @RequestParam Map<String, Object> param) {
       /* logger.info("{}验证章节名称是否存在入参:",param);
        Integer id = caricatureChapterService.validateCaricatureChapterName(param);
        logger.info("{}验证章节名称是否存在出参:",id);
        if(id > 0){
            return "false";
        }*/
        return "true";
    }


}
