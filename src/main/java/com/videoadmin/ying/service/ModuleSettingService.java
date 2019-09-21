package com.videoadmin.ying.service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.ModuleSettingMapper;
import com.videoadmin.ying.po.ModuleSetting;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author hong
 * @Date 19-9-10
 */
@Service
public class ModuleSettingService extends BaseService<ModuleSetting> {


    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Map<String, Object> findOne(Integer id){
        return ((ModuleSettingMapper)mapper).findOne(id);
    }


    public void update(List<ModuleSetting> moduleSettingList){
        for (ModuleSetting moduleSetting : moduleSettingList) {
            mapper.updateById(moduleSetting);
        }
    }

}
