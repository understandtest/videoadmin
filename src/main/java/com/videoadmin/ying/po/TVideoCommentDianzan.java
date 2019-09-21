package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 视频评论点赞
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@TableName("t_video_comment_dianzan")
@SuppressWarnings("serial")
public class TVideoCommentDianzan extends BaseModel {

      /**
     * 视频ID
     */
         	   @TableField("video_comment_id")
        	private Integer videoCommentId;
      /**
     * 会员ID
     */
         	   @TableField("member_id")
        	private Integer memberId;


	public Integer getVideoCommentId() {
		return videoCommentId;
	}

	public void setVideoCommentId(Integer videoCommentId) {
		this.videoCommentId = videoCommentId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

}