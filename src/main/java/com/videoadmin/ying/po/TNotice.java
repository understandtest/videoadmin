package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * 公告信息
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@TableName("t_notice")
@SuppressWarnings("serial")
public class TNotice extends BaseModel {

      /**
     * 标题
     */
         	   @TableField("notice_title")
        	private String noticeTitle;
      /**
     * 简介
     */
         	   @TableField("notice_brief")
        	private String noticeBrief;
      /**
     * 内容
     */
         	   @TableField("notice_content")
        	private String noticeContent;
         	  /**
         	     * 有效开始时间
         	     */
         	         	   @TableField("start_time")
         	        	private Date startTime;

         	         	 /**
         	         	     * 有效结束时间
         	         	     */
         	         	         	   @TableField("end_time")
         	         	        	private Date endTime;

      /**
     * 提醒有效时长
     */
         	   @TableField("long_time")
        	private Float longTime;


	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeBrief() {
		return noticeBrief;
	}

	public void setNoticeBrief(String noticeBrief) {
		this.noticeBrief = noticeBrief;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public Float getLongTime() {
		return longTime;
	}

	public void setLongTime(Float longTime) {
		this.longTime = longTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}