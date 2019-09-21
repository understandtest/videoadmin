package com.videoadmin.ying.dao;


import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.CaricatureBookcase;
import org.apache.ibatis.annotations.Param;

/**
 * Author lbh
 * Date 2019-07-27
 */
public interface CaricatureBookcaseMapper extends BaseMapper<CaricatureBookcase> {
    void delByCaricatureId(@Param("caricatureId") int caricatureId);
}
