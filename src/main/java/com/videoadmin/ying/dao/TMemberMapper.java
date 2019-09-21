package com.videoadmin.ying.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.TMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
public interface TMemberMapper extends BaseMapper<TMember> {
        public List<Map<String, Object>> selectListPage(Page<Map<String, Object>> page, @Param("cm") Map<String, Object> paramMap);
//    List<Map<String, Object>> selectListPage(@Param("cm") Map<String, Object> paramMap);

    public Map<String, Object> getMemberInfo(@Param("cm") Map<String, Object> paramMap);

    void awardIntegral(@Param("integralNumber") Integer integralNumber);

    long countListPage(@Param("cm") Map<String, Object> paramMap);
}