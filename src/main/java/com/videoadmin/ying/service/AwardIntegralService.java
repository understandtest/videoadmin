package com.videoadmin.ying.service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TMemberMapper;
import com.videoadmin.ying.po.AwardIntegral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author hong
 * @Date 19-9-10
 */
@Service
public class AwardIntegralService extends BaseService<AwardIntegral> {

    @Autowired
    private TMemberMapper memberMapper;

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public AwardIntegral findOne(Integer id){
        return mapper.selectById(id);
    }

    /**
     * 保存奖励积分，并赠送积分给用户
     * @param awardIntegral
     */
    @Transactional
    public void save(AwardIntegral awardIntegral){

        Integer id = awardIntegral.getId();

        if(null == id){
            awardIntegral.setId(1);
        }

        awardIntegral.setUpdateTime(new Date());

        mapper.updateById(awardIntegral);

        // 赠送积分
        memberMapper.awardIntegral(awardIntegral.getIntegralNumber());
    }



}
