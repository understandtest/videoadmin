package com.videoadmin.ying.controller;

import com.videoadmin.base.BaseController;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.PropertiesUtil;
import com.videoadmin.ying.po.SysServer;
import com.videoadmin.ying.service.SysServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AUTHER lbh
 * Date 2019/5/21
 */
@Controller
@RequestMapping("/admin/sysServer")
public class SysServerController extends BaseController {

    @Autowired
    private SysServerService sysServerService;

    @GetMapping("/list")
    public String list(Model model){
        //查询所有的服务器节点
        List<SysServer> servers = sysServerService.findAll();

        model.addAttribute("list",servers);

        return "/serverManager/node/list";
    }


    @GetMapping("/findAll")
    @ResponseBody
    public Object findAll(ModelMap modelMap){
        List<SysServer> servers = sysServerService.findAll();

        return setSuccessResponse(modelMap,servers);
    }

    @RequestMapping(value = "/toEdit")
    public Object toUpdate(ModelMap modelMap, @RequestParam(required=false) Map<String, Object> param) {
        if(DataUtil.isNotEmpty(param)){
            if (param.get("id")!=null && !("").equals(param.get("id").toString())) {
                logger.info("{}查询等级列表 start...{}", param);
                String id = param.get("id").toString();
                SysServer server = sysServerService.findOne(Integer.parseInt(id));
                modelMap.addAttribute("server", server);
                modelMap.addAttribute("types", "编辑");
            }else{
                modelMap.addAttribute("types", "添加");
            }
        }
        return "/serverManager/node/edit";
    }

    @RequestMapping(value = "/detail")
    public Object detail(ModelMap modelMap, HttpServletRequest request) {
        String id = request.getParameter("id");
        if(DataUtil.isNotEmpty(id)){
            SysServer server = sysServerService.findOne(Integer.parseInt(id));
            modelMap.addAttribute("server",server);
        }
        modelMap.put("prefix", PropertiesUtil.getString("remote.file.uri.prefix"));
        return "/serverManager/node/detail";
    }

    @PostMapping("/save")
    @ResponseBody
    public Object save(ModelMap modelMap,SysServer sysServer){

        sysServerService.save(sysServer);

        return setSuccessResponse(modelMap);

    }

    @PostMapping("/validateName")
    @ResponseBody
    public Object validateName(ModelMap modelMap, @RequestParam Map<String, Object> param) {
        return "true";
    }


    @RequestMapping(value = "/del")
    @ResponseBody
    public Object delDataTypeByIds(ModelMap modelMap, @RequestParam String ids) {
        logger.info("删除服务器节点数据，删除主键为：" + ids);
        Map<String,Object> map=new HashMap<String,Object>();
        if(DataUtil.isNotEmpty(ids)){
            String[] strs=ids.split(",");
            for (String string : strs) {
                int id=Integer.valueOf(string);
                sysServerService.delete(id);
            }
        }

        return setSuccessResponse(modelMap);
    }



}
