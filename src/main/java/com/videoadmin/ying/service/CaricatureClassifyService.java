package com.videoadmin.ying.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.CaricatureClassifyMapper;
import com.videoadmin.ying.dao.TClassifyMapper;
import com.videoadmin.ying.po.CaricatureClassify;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author lbh
 * Date 2019-07-25
 */
@Service
public class CaricatureClassifyService extends BaseService<CaricatureClassify> {


    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
        logger.info("分类查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("分类查询，入参："+page);
        page.setRecords(((CaricatureClassifyMapper)mapper).selectListPage(page, paramMap));
        logger.info("分类查询，出参："+page.toString());
        return page;
    }

    public Integer validateName(Map<String,Object> paraMap){
        return ((CaricatureClassifyMapper)mapper).validateName(paraMap);
    }

    public List<CaricatureClassify> findAll() {
        return ((CaricatureClassifyMapper)mapper).findAll();
    }
}
