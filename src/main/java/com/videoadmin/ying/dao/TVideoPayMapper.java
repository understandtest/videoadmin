package com.videoadmin.ying.dao;

import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.TVideoPay;
import org.apache.ibatis.annotations.Param;

/**
 * AUTHER lbh
 * Date 2019/5/21
 */
public interface TVideoPayMapper extends BaseMapper<TVideoPay> {


    Integer deleteByVideoId(@Param("videoId") Integer videoId);
}
