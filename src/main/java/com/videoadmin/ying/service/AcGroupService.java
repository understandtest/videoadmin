package com.videoadmin.ying.service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.po.AcGroup;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author lbh
 * @Date 2019/8/7
 */
@Service
public class AcGroupService extends BaseService<AcGroup> {


    public AcGroup findOne(Integer id){
        return mapper.selectById(id);
    }


    /**
     * 添加或者更新
     * @param acGroup
     * @return
     */
    public void save(AcGroup acGroup){

        if(null == acGroup.getId()){ //插入数据
            acGroup.setId(1);
            acGroup.setCreateTime(new Date());
            mapper.insert(acGroup);
            return;
        }

        mapper.updateById(acGroup);
    }

}
