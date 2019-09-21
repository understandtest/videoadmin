package com.videoadmin.ying.po;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import java.io.Serializable;

/**
 * <p>
 * 影片
 * </p>
 * 
 * @author haha
 * @since 2018-10-05
 */
@TableName("t_video")
@SuppressWarnings("serial")
public class TVideo extends BaseModel {

	/**
	 * 视频名称
	 */
	@TableField("video_name")
	private String videoName;
	/**
	 * 视频上传类型 1=系统 2=外部
	 */
	@TableField("video_type")
	private Integer videoType;
	/**
	 * 视频链接
	 */
	@TableField("video_url")
	private String videoUrl;

	/**
	 * 视频时长
	 */
	@TableField("play_time")
	private Double playTime;
	/**
	 * 明星ID
	 */
	@TableField("star_id")
	private Integer starId;
	/**
	 * 分类ID
	 */
	@TableField("classify_id")
	private Integer classifyId;
	/**
	 * 上映时间
	 */
	@TableField("push_time")
	private Date pushTime;
	/**
	 * 是否上架 0=否 1=是
	 */
	@TableField("is_push")
	private Integer isPush;
	/**
	 * 简介
	 */
	@TableField("brief_content")
	private String briefContent;
	/**
	 * 视频封面上传类型 1=系统 2=外部
	 */
	@TableField("video_cover_type")
	private Integer videoCoverType;
	/**
	 * 封面
	 */
	@TableField("video_cover")
	private String videoCover;
	/**
	 * 播放次数
	 */
	@TableField("play_num")
	private Integer playNum;
	/**
	 * 失效次数
	 */
	@TableField("lose_num")
	private Integer loseNum;
	/**
	 * 渠道ID
	 */
	@TableField("channel_id")
	private Integer channelId;
	/**
	 * 原始链接
	 */
	@TableField("source_url")
	private String sourceUrl;
	/**
	 * 原始ID
	 */
	@TableField("source_id")
	private String sourceId;

	/**
	 * 分类一级id
	 */
	@TableField("tag_type_id")
	private Integer tagTypeId;

	public Integer getTagTypeId() {
		return tagTypeId;
	}

	public void setTagTypeId(Integer tagTypeId) {
		this.tagTypeId = tagTypeId;
	}

	public Double getPlayTime() {
		return playTime;
	}

	public void setPlayTime(Double playTime) {
		this.playTime = playTime;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getStarId() {
		return starId;
	}

	public void setStarId(Integer starId) {
		this.starId = starId;
	}

	public Integer getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(Integer classifyId) {
		this.classifyId = classifyId;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public Integer getIsPush() {
		return isPush;
	}

	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}

	public String getBriefContent() {
		return briefContent;
	}

	public void setBriefContent(String briefContent) {
		this.briefContent = briefContent;
	}

	public String getVideoCover() {
		return videoCover;
	}

	public void setVideoCover(String videoCover) {
		this.videoCover = videoCover;
	}

	public Integer getPlayNum() {
		return playNum;
	}

	public void setPlayNum(Integer playNum) {
		this.playNum = playNum;
	}

	public Integer getLoseNum() {
		return loseNum;
	}

	public void setLoseNum(Integer loseNum) {
		this.loseNum = loseNum;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getVideoType() {
		return videoType;
	}

	public void setVideoType(Integer videoType) {
		this.videoType = videoType;
	}

	public Integer getVideoCoverType() {
		return videoCoverType;
	}

	public void setVideoCoverType(Integer videoCoverType) {
		this.videoCoverType = videoCoverType;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
}