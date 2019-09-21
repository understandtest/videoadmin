package com.videoadmin.ying.po;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 会员点心记录
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@TableName("t_care_history")
@SuppressWarnings("serial")
public class TCareHistory extends BaseModel {

      /**
     * 会员ID
     */
         	   @TableField("member_id")
        	private Integer memberId;
      /**
     * 视频ID
     */
         	   @TableField("video_id")
        	private Integer videoId;
      /**
     * 点爱心时间
     */
         	   @TableField("dian_time")
        	private Date dianTime;


	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public Date getDianTime() {
		return dianTime;
	}

	public void setDianTime(Date dianTime) {
		this.dianTime = dianTime;
	}

}