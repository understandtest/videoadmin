package com.videoadmin.ying.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.TMemberLoginMapper;
import com.videoadmin.ying.po.TMemberLogin;

/**
 * <p>
 * 用户登录表  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tMemberLoginService")
public class TMemberLoginService extends BaseService<TMemberLogin> {
	public Integer getMemberLoginId(Map<String, Object> paramMap){
        logger.info("开始查询会员信息");
        return ((TMemberLoginMapper)mapper).getMemberLoginId(paramMap);
    }
	public List<Map<String, Object>> queryFromCodeList(Map<String, Object> paramMap){
	    logger.info("查询渠道包数据，入参："+paramMap);
	    List<Map<String, Object>> list=((TMemberLoginMapper)mapper).queryFromCodeList(paramMap);
        logger.info("查询渠道包数据，出参："+list);
        return list;
	}
}