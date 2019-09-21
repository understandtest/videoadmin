package com.videoadmin.ying.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.IntegralVipMapper;
import com.videoadmin.ying.po.IntegralVip;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @Author hong
 * @Date 19-8-26
 */
@Service
public class IntegralVipService extends BaseService<IntegralVip> {


    public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap) {
        logger.info("积分兑换查询，入参：" + paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("积分兑换查询，入参：" + page);
        page.setRecords(((IntegralVipMapper) mapper).selectListPage(page, paramMap));
        logger.info("积分兑换查询，出参：" + page.toString());
        return page;
    }

    public IntegralVip findOne(Integer id){
        return mapper.selectById(id);
    }


    public void save(IntegralVip integralVip) {

        if (null != integralVip.getId()) {
            integralVip.setUpdateTime(new Date());
            mapper.updateById(integralVip);
        } else {
            integralVip.setCreateTime(new Date());
            mapper.insert(integralVip);
        }
    }


}
