package com.videoadmin.utils.schedule;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.videoadmin.utils.SpringContextUtils;
import com.videoadmin.ying.po.ScheduleJob;
import com.videoadmin.ying.po.ScheduleJobLog;
import com.videoadmin.ying.service.ScheduleJobLogService;

public class BaseScheduleJob extends QuartzJobBean{

    protected final Logger logger = LogManager.getLogger(this.getClass());
	private ExecutorService service = Executors.newSingleThreadExecutor(); 
	public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";
	
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	//todo 执行自动任务
    	ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(JOB_PARAM_KEY);
        
    	
        //获取spring bean
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");
    	System.out.println("scheduleJobLogService---:"+scheduleJobLogService);
        //数据库保存执行记录
    	ScheduleJobLog log = new ScheduleJobLog();
        log.setJobId(scheduleJob.getId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setMethodName(scheduleJob.getMethodName());
        log.setParams(scheduleJob.getParams());
        log.setCreateTime(new Date());
        
        //任务开始时间
        long startTime = System.currentTimeMillis();
        
        try {
            //执行任务
        	logger.info("任务准备执行，任务ID：" + scheduleJob.getId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(),scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = service.submit(task);
            
			future.get();
			
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			log.setTimes((int)times);
			//任务状态    0：成功    1：失败
			log.setStatus(0);
			
			logger.info("任务执行完毕，任务ID：" + scheduleJob.getId() + "  总共耗时：" + times + "毫秒");
		} catch (Exception e) {
			logger.error("任务执行失败，任务ID：" + scheduleJob.getId(), e);
			
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			log.setTimes((int)times);
			
			//任务状态    0：成功    1：失败
			log.setStatus(1);
			log.setError(StringUtils.substring(e.toString(), 0, 2000));
		}finally {
			scheduleJobLogService.update(log);
		}
    }
}
