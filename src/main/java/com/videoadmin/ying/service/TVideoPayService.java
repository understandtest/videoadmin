package com.videoadmin.ying.service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TVideoPayMapper;
import com.videoadmin.ying.po.TVideoPay;
import org.springframework.stereotype.Service;

/**
 * AUTHER lbh
 * Date 2019/5/21
 */
@Service
public class TVideoPayService extends BaseService<TVideoPay> {

    /**
     * 根据视频id删除播放记录
     * @param videoId 视频id
     */
    public void deleteByVideoId(Integer videoId){
        ((TVideoPayMapper)mapper).deleteByVideoId(videoId);
    }

}
