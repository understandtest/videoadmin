package com.videoadmin.ying.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.CaricatureChapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Author lbh
 * Date 2019-07-25
 */
public interface CaricatureChapterMapper extends BaseMapper<CaricatureChapter> {
    List<Integer> selectByCaricatureId(@Param("caricatureId") int caricatureId);

    List<Map<String, Object>> selectListPage(Page<Map<String, Object>> page, Map<String, Object> paraMap);

    Integer validateCaricatureChapterName(Map<String, Object> param);
}
