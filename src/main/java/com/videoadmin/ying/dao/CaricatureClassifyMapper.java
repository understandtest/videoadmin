package com.videoadmin.ying.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.CaricatureClassify;

import java.util.List;
import java.util.Map;

/**
 * Author lbh
 * Date 2019-07-25
 */
public interface CaricatureClassifyMapper extends BaseMapper<CaricatureClassify> {
    List<Map<String, Object>> selectListPage(Page<Map<String, Object>> page, Map<String, Object> paramMap);

    Integer validateName(Map<String, Object> paraMap);

    /**
     * 查询所有的分类，但排序全部分类
     * @return
     */
    List<CaricatureClassify> findAll();
}
