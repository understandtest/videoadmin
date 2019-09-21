package com.videoadmin.ying.dao;

import com.videoadmin.ying.po.TVideoTags;
import com.videoadmin.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
public interface TVideoTagsMapper extends BaseMapper<TVideoTags> {

    List<TVideoTags> selectByVideoId(@Param("videoId") Integer videoId);

    List<Integer> findTagIdsByVideoId(@Param("videoId") Integer videoId);
}