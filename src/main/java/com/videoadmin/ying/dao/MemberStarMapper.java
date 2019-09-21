package com.videoadmin.ying.dao;

import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.MemberStar;
import org.apache.ibatis.annotations.Param;

/**
 * @Author hong
 * @Date 19-9-10
 */
public interface MemberStarMapper extends BaseMapper<MemberStar> {
    Integer delMemberCollectByStarId(@Param("starId") int starId);
}
