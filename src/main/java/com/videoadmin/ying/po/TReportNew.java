package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author haha
 * @since 2018-11-01
 */
@TableName("t_report_new")
@SuppressWarnings("serial")
public class TReportNew extends BaseModel {

      /**
     * 当前时间
     */
         	   @TableField("curent_day")
        	private String curentDay;
      /**
     * 使用类型 1=注册 2=登录 3=充值 4=绑定手机号
     */
         	   @TableField("action_type")
        	private Integer actionType;
      /**
     * 渠道码
     */
         	   @TableField("from_code")
        	private String fromCode;
      /**
     * 充值次数
     */
         	   @TableField("rechange_num")
        	private Integer rechangeNum;
      /**
     * 充值金额
     */
         	   @TableField("rechange_price")
        	private Double rechangePrice;
         	 
         	  @TableField("times_num")
         	private Integer timesNum;


	public String getCurentDay() {
		return curentDay;
	}

	public void setCurentDay(String curentDay) {
		this.curentDay = curentDay;
	}

	public Integer getActionType() {
		return actionType;
	}

	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}

	public String getFromCode() {
		return fromCode;
	}

	public void setFromCode(String fromCode) {
		this.fromCode = fromCode;
	}

	public Integer getRechangeNum() {
		return rechangeNum;
	}

	public void setRechangeNum(Integer rechangeNum) {
		this.rechangeNum = rechangeNum;
	}

	public Double getRechangePrice() {
		return rechangePrice;
	}

	public void setRechangePrice(Double rechangePrice) {
		this.rechangePrice = rechangePrice;
	}

	public Integer getTimesNum() {
		return timesNum;
	}

	public void setTimesNum(Integer timesNum) {
		this.timesNum = timesNum;
	}

}