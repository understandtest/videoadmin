package com.videoadmin.utils.schedule;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.videoadmin.exception.RRException;
import com.videoadmin.utils.SysConstants.ScheduleStatus;
import com.videoadmin.ying.po.ScheduleJob;

public class ScheduleUtils {
	 private final static String JOB_NAME = "TASK_";
	 public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";
	    
	    /**
	     * 获取触发器key
	     */
	    public static TriggerKey getTriggerKey(Integer jobId) {
	        return TriggerKey.triggerKey(JOB_NAME + jobId);
	    }
	    
	    /**
	     * 获取jobKey
	     */
	    public static JobKey getJobKey(Integer jobId) {
	        return JobKey.jobKey(JOB_NAME + jobId);
	    }

	    /**
	     * 获取表达式触发器
	     */
	    public static CronTrigger getCronTrigger(Scheduler scheduler, Integer jobId) {
	        try {
	            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
	        } catch (SchedulerException e) {
	            throw new RRException("获取定时任务CronTrigger出现异常", e);
	        }
	    }

	    /**
	     * 创建定时任务
	     */
	    public static void createScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) {
	        try {
	        	//构建job信息
	            JobDetail jobDetail = JobBuilder.newJob(BaseScheduleJob.class).withIdentity(getJobKey(scheduleJob.getId())).build();

	            //表达式调度构建器
	            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

	            //按新的cronExpression表达式构建一个新的trigger
	            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getId())).withSchedule(scheduleBuilder).build();

	            //放入参数，运行时的方法可以获取
	            jobDetail.getJobDataMap().put(JOB_PARAM_KEY, scheduleJob);
	            
	            scheduler.scheduleJob(jobDetail, trigger);
	            
	            //暂停任务
	            if(scheduleJob.getStatus() == ScheduleStatus.PAUSE.getValue()){
	            	pauseJob(scheduler, scheduleJob.getId());
	            }
	        } catch (SchedulerException e) {
	            throw new RRException("创建定时任务失败", e);
	        }
	    }
	    
	    /**
	     * 更新定时任务
	     */
	    public static void updateScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) {
	        try {
	            TriggerKey triggerKey = getTriggerKey(scheduleJob.getId());

	            //表达式调度构建器
	            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

	            CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getId());
	            
	            //按新的cronExpression表达式重新构建trigger
	            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
	            
	            //参数
	            trigger.getJobDataMap().put(JOB_PARAM_KEY, scheduleJob);
	            
	            scheduler.rescheduleJob(triggerKey, trigger);
	            
	            //暂停任务
	            if(scheduleJob.getStatus() == ScheduleStatus.PAUSE.getValue()){
	            	pauseJob(scheduler, scheduleJob.getId());
	            }
	            
	        } catch (SchedulerException e) {
	            throw new RRException("更新定时任务失败", e);
	        }
	    }

	    /**
	     * 立即执行任务
	     */
	    public static void run(Scheduler scheduler, ScheduleJob scheduleJob) {
	        try {
	        	//参数
	        	JobDataMap dataMap = new JobDataMap();
	        	dataMap.put(JOB_PARAM_KEY, scheduleJob);
	        	
	            scheduler.triggerJob(getJobKey(scheduleJob.getId()), dataMap);
	        } catch (SchedulerException e) {
	            throw new RRException("立即执行定时任务失败", e);
	        }
	    }

	    /**
	     * 暂停任务
	     */
	    public static void pauseJob(Scheduler scheduler, Integer jobId) {
	        try {
	            scheduler.pauseJob(getJobKey(jobId));
	        } catch (SchedulerException e) {
	            throw new RRException("暂停定时任务失败", e);
	        }
	    }

	    /**
	     * 恢复任务
	     */
	    public static void resumeJob(Scheduler scheduler, Integer jobId) {
	        try {
	            scheduler.resumeJob(getJobKey(jobId));
	        } catch (SchedulerException e) {
	            throw new RRException("暂停定时任务失败", e);
	        }
	    }

	    /**
	     * 删除定时任务
	     */
	    public static void deleteScheduleJob(Scheduler scheduler, Integer jobId) {
	        try {
	            scheduler.deleteJob(getJobKey(jobId));
	        } catch (SchedulerException e) {
	            throw new RRException("删除定时任务失败", e);
	        }
	    }
}
