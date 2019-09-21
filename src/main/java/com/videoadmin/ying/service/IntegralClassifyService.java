package com.videoadmin.ying.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.IntegralClassifyMapper;
import com.videoadmin.ying.po.IntegralClassify;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author hong
 * @Date 19-8-26
 */
@Service
public class IntegralClassifyService extends BaseService<IntegralClassify> {

    public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap) {
        logger.info("积分类别，入参：" + paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("积分类别，入参：" + page);
        page.setRecords(((IntegralClassifyMapper) mapper).selectListPage(page, paramMap));
        logger.info("积分类别，出参：" + page.toString());
        return page;
    }

    public void updateById(IntegralClassify integralClassify) {

        mapper.updateById(integralClassify);


    }
}
