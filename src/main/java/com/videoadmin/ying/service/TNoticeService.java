package com.videoadmin.ying.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.utils.StringUtils;
import com.videoadmin.ying.dao.TNoticeMapper;
import com.videoadmin.ying.po.TNotice;

/**
 * <p>
 * 公告信息  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tNoticeService")
public class TNoticeService extends BaseService<TNotice> {
	@Autowired 
	private TNoticeMapper tNoticeMapper;
	
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("公告查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("公告查询，入参："+page);
        page.setRecords(tNoticeMapper.selectListPage(page, paramMap));
        logger.info("公告查询，出参："+page.toString());
        return page;
	}
	public Integer validateName(Map<String,Object> paraMap){
		return tNoticeMapper.validateName(paraMap);
	}
	@Transactional
	public void addOrUpdata(Map<String, Object> param) {
	    if (param!=null)
        {
	      //更新当前表
	    TNotice tNotice=new TNotice();
	        if (param.get("noticeTitle")!=null&& !("").equals(param.get("noticeTitle").toString()))
	        {
	        	tNotice.setNoticeTitle(param.get("noticeTitle").toString());
	        }
	        if (param.get("startTime")!=null&& !("").equals(param.get("startTime").toString()))
	        {
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        	 try {
					Date date = sdf.parse(param.get("startTime").toString());
					tNotice.setStartTime(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        if (param.get("endTime")!=null&& !("").equals(param.get("endTime").toString()))
	        {
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        	 try {
					Date date = sdf.parse(param.get("endTime").toString());
					tNotice.setEndTime(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        if (param.get("longTime")!=null&& !("").equals(param.get("longTime").toString()))
	        {
	        	tNotice.setLongTime(Float.valueOf(param.get("longTime").toString()));
	        }
	        if (param.get("noticeBrief")!=null&& !("").equals(param.get("noticeBrief").toString()))
	        {
	        	tNotice.setNoticeBrief(param.get("noticeBrief").toString());
	        }
	        if (param.get("noticeContent")!=null&& !("").equals(param.get("noticeContent").toString()))
	        {
	        	tNotice.setNoticeContent(param.get("noticeContent").toString());
	        }
	       
	        if(null != param.get("id") && !"".equals(param.get("id").toString())){
				//更新
	        	tNotice.setId(Integer.valueOf(param.get("id").toString()));
	        	//tNotice.setUpdateBy(getCurrUser().getUserId());
	        	tNotice.setUpdateTime(new Date());
	        	//更新公告表
	            logger.info("{}更新公告表 start...{}", tNotice);
	            tNoticeMapper.updateById(tNotice);
			}else{
				//新增
				//tNotice.setCreateBy(getCurrUser().getUserId());
				tNotice.setCreateTime(new Date());
				tNoticeMapper.insert(tNotice);
			}
        }
	}
}