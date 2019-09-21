package com.videoadmin.ying.controller;

import com.videoadmin.base.BaseController;
import com.videoadmin.utils.redis.CacheUtil;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AUTHER lbh
 * Date 2019/5/22
 */
@RestController
@RequestMapping("/admin/redis")
public class RedisController extends BaseController {


    /**
     * 清理缓存
     * @param modelMap
     * @return
     */
    @GetMapping("/clearAll")
    public Object clearAll(ModelMap modelMap){
        CacheUtil.getRedisHelper().delAll("*");
        return setSuccessResponse(modelMap);
    }

    /**
     * 清理首页缓存
     * @param modelMap
     * @return
     */
    @GetMapping("/{path}")
    public Object clearByPath(ModelMap modelMap,@PathVariable("path") String path){
        CacheUtil.getRedisHelper().del(path);
        return setSuccessResponse(modelMap);
    }
}
