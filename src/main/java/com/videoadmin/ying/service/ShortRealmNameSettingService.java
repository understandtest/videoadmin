package com.videoadmin.ying.service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.po.ShortRealmNameSetting;
import org.springframework.stereotype.Service;

/**
 * Author lbh
 * Date 2019-07-24
 */
@Service
public class ShortRealmNameSettingService extends BaseService<ShortRealmNameSetting> {

    /**
     * 查询短域名设置信息
     * @return
     */
    public ShortRealmNameSetting findOne(){
        return mapper.selectById(1);
    }

    public void save(ShortRealmNameSetting shortRealmNameSetting){

        if(null == shortRealmNameSetting.getId()){
            shortRealmNameSetting.setId(1);
        }

        mapper.updateById(shortRealmNameSetting);


    }


}
