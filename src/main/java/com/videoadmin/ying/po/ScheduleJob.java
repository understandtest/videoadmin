package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 定时任务
 * </p>
 *
 * @author haha
 * @since 2018-11-01
 */
@TableName("schedule_job")
@SuppressWarnings("serial")
public class ScheduleJob extends BaseModel {

      /**
     * spring bean名称
     */
         	   @TableField("bean_name")
        	private String beanName;
      /**
     * 方法名
     */
         	   @TableField("method_name")
        	private String methodName;
      /**
     * 参数
     */
            	private String params;
      /**
     * cron表达式
     */
         	   @TableField("cron_expression")
        	private String cronExpression;
      /**
     * 任务状态
     */
            	private Integer status;
      /**
     * 备注
     */
            	private String remark;


	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}