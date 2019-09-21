package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 定时任务日志
 * </p>
 *
 * @author haha
 * @since 2018-11-01
 */
@TableName("schedule_job_log")
@SuppressWarnings("serial")
public class ScheduleJobLog extends BaseModel {

      /**
     * 任务id
     */
         	   @TableField("job_id")
        	private Integer jobId;
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
     * 任务状态    0：成功    1：失败
     */
            	private Integer status;
      /**
     * 失败信息
     */
            	private String error;
      /**
     * 耗时(单位：毫秒)
     */
            	private Integer times;
            	


	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

}