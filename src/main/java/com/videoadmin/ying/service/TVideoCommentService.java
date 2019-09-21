package com.videoadmin.ying.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.SysLoginMapper;
import com.videoadmin.ying.dao.TVideoCommentMapper;
import com.videoadmin.ying.po.TVideoComment;

/**
 * <p>
 * 视频评论  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tVideoCommentService")
public class TVideoCommentService extends BaseService<TVideoComment> {
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("视频评论查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("视频评论查询，入参："+page);
        page.setRecords(((TVideoCommentMapper)mapper).selectListPage(page, paramMap));
        logger.info("视频评论查询，出参："+page.toString());
        return page;
	}
	 public Map<String, Object> getVideoCommentInfo(Map<String, Object> paramMap){
	        logger.info("开始查询视频评论信息");
	        return ((TVideoCommentMapper)mapper).getVideoCommentInfo(paramMap);
	    }
}