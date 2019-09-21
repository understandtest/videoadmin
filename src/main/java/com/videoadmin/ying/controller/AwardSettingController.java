package com.videoadmin.ying.controller;

import com.videoadmin.base.BaseController;
import com.videoadmin.utils.StringUtils;
import com.videoadmin.ying.po.AwardIntegral;
import com.videoadmin.ying.po.FreeViewNumberSetting;
import com.videoadmin.ying.service.AwardIntegralService;
import com.videoadmin.ying.service.FreeViewNumberSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author lbh
 * @Date 19-8-17
 */
@Controller
@RequestMapping("/admin/awardSetting")
public class AwardSettingController extends BaseController {

    @Autowired
    private FreeViewNumberSettingService freeViewNumberSettingService;

    @Autowired
    private AwardIntegralService awardIntegralService;

    @GetMapping("/toEdit")
    public String toEdit(ModelMap modelMap){
        //查询免费播放次数
        FreeViewNumberSetting frn = freeViewNumberSettingService.findOne(1);

        modelMap.put("frn",frn);

        // 查询积分赠送
        AwardIntegral awardIntegral = awardIntegralService.findOne(1);

        modelMap.put("awardIntegral",awardIntegral);

        return "/systemSetting/awardSetting/edit";
    }

    @PostMapping("/save")
    @ResponseBody
    public Object save(ModelMap modelMap,@RequestParam Map<String,Object> params){

        // 保存免费观看次数
        FreeViewNumberSetting freeViewNumberSetting = new FreeViewNumberSetting();

        String frnId = (String)params.get("frnId");

        if(StringUtils.isNotEmpty(frnId)){
            freeViewNumberSetting.setId(Integer.parseInt(frnId));
        }else{
            freeViewNumberSetting.setId(1);
        }

        String freeNumber = (String)params.get("freeNumber");

        if(StringUtils.isNotEmpty(freeNumber)){
            freeViewNumberSetting.setFreeNumber(Integer.parseInt(freeNumber));
        }else{
            freeViewNumberSetting.setFreeNumber(0);
        }

        freeViewNumberSettingService.save(freeViewNumberSetting);

        // 保存积分奖励数量
        AwardIntegral awardIntegral = new AwardIntegral();

        String awardIntegralId = (String)params.get("awardIntegralId");

        if(StringUtils.isNotEmpty(awardIntegralId)){
            awardIntegral.setId(Integer.parseInt(awardIntegralId));
        }else{
            awardIntegral.setId(1);
        }

        String integralNumber = (String)params.get("integralNumber");

        if(StringUtils.isNotEmpty(integralNumber)){
            awardIntegral.setIntegralNumber(Integer.parseInt(integralNumber));
        }else{
            awardIntegral.setIntegralNumber(0);
        }

        awardIntegralService.save(awardIntegral);

        return setSuccessResponse(modelMap);

    }


}
