package com.videoadmin.ying.service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.MemberStarMapper;
import com.videoadmin.ying.po.MemberStar;
import org.springframework.stereotype.Service;

/**
 * @Author hong
 * @Date 19-9-10
 */
@Service
public class MemberStarService extends BaseService<MemberStar> {


    /**
     * 根据明星id删除用户收藏
     * @param starId
     */
    public void delMemberCollectByStarId(int starId) {
        ((MemberStarMapper)mapper).delMemberCollectByStarId(starId);
    }
}
