package com.videoadmin.ying.service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.po.VideoDuration;
import org.springframework.stereotype.Service;

/**
 * Author lbh
 * Date 2019/6/14
 */
@Service
public class VideoDurationService extends BaseService<VideoDuration> {


    public VideoDuration getVideoDuration(){
        return mapper.selectById(1);
    }

    public void updateVideoDuration(VideoDuration videoDuration){
        mapper.updateById(videoDuration);
    }

}
