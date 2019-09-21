package com.videoadmin.ying.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.Caricature;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Author lbh
 * Date 2019-07-25
 */

public interface CaricatureMapper extends BaseMapper<Caricature> {

    List<Map<String, Object>> selectListPage(Page<Map<String, Object>> page, @Param("cm") Map<String, Object> paramMap);

    Integer validateCaricatureName(Map<String, Object> param);
}
