package com.videoadmin.ying.service;

import com.videoadmin.ying.dao.TRateMapper;
import com.videoadmin.ying.dao.TVideoTagsMapper;
import com.videoadmin.ying.po.TVideoTags;
import com.videoadmin.base.BaseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 视频标签  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tVideoTagsService")
public class TVideoTagsService extends BaseService<TVideoTags> {

    public List<TVideoTags> selectByVideoId(Integer videoId){
        return ((TVideoTagsMapper)mapper).selectByVideoId(videoId);
    }

    public List<Integer> findTagIdsByVideoId(Integer videoId) {
        return ((TVideoTagsMapper)mapper).findTagIdsByVideoId(videoId);
    }
}