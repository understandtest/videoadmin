package com.videoadmin.ying.service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.po.GestureSetting;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author hong
 * @Date 19-8-26
 */
@Service
public class GestureSettingService extends BaseService<GestureSetting> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public GestureSetting findOne(Integer id){
        return mapper.selectById(id);
    }

    /**
     * 保存手势设置
     * @param gestureSetting
     */
    public void save(GestureSetting gestureSetting){

        Integer id = gestureSetting.getId();

        //防止id为空
        if(null == id){
            gestureSetting.setId(1);
        }

        gestureSetting.setUpdateTime(new Date());

        mapper.updateById(gestureSetting);
    }

}
