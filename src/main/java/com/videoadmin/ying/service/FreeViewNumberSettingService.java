package com.videoadmin.ying.service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.po.FreeViewNumberSetting;
import com.videoadmin.ying.po.TMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author lbh
 * @Date 19-8-17
 */
@Service
@Transactional
public class FreeViewNumberSettingService extends BaseService<FreeViewNumberSetting> {

    @Autowired
    private TMemberService memberService;

    public FreeViewNumberSetting findOne(Integer id) {
        return mapper.selectById(id);
    }


    /**
     * 保存免费观影次数
     *
     * @param freeViewNumberSetting
     */
    public void save(FreeViewNumberSetting freeViewNumberSetting) {

        freeViewNumberSetting.setId(1);
        //直接更新
        mapper.updateById(freeViewNumberSetting);

        //更新所有用户的观影次数
        logger.info("更新用户的观影次数,当前系统的观影次数为:{}",freeViewNumberSetting.getFreeNumber());

        List<TMember> tMembers = memberService.selectList(null);

        tMembers.forEach(m -> {
            m.setViewNum(freeViewNumberSetting.getFreeNumber());
            memberService.update(m);
        });
    }


}
