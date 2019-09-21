package com.videoadmin.ying.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.utils.SysConstants.ScheduleStatus;
import com.videoadmin.utils.schedule.ScheduleUtils;
import com.videoadmin.ying.dao.ScheduleJobMapper;
import com.videoadmin.ying.dao.SysRoleMapper;
import com.videoadmin.ying.po.ScheduleJob;

/**
 * <p>
 * 定时任务  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-11-01
 */
@Service("scheduleJobService")
public class ScheduleJobService extends BaseService<ScheduleJob> {
	
	@Autowired
    private Scheduler scheduler;
	
	 /* 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		List<ScheduleJob> scheduleJobList = ((ScheduleJobMapper)mapper).queryList(new HashMap<String, Object>());
		for(ScheduleJob scheduleJob : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
		}
	}
	
	
	public ScheduleJob queryObject(Integer jobId) {
		return ((ScheduleJobMapper)mapper).selectById(jobId);
	}

	public List<ScheduleJob> queryList(Map<String, Object> map) {
		return ((ScheduleJobMapper)mapper).queryList(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return ((ScheduleJobMapper)mapper).queryTotal(map);
	}

	@Transactional
	public void save(ScheduleJob scheduleJob) {
		scheduleJob.setCreateTime(new Date());
		scheduleJob.setStatus(ScheduleStatus.NORMAL.getValue());
        this.update(scheduleJob);
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }
	
	@Transactional
	public void updateInfo(ScheduleJob scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
        this.update(scheduleJob);       
    }

	
	@Transactional
    public void deleteBatch(Integer[] jobIds) {
    	for(Integer jobId : jobIds){
    		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
    	}
    	
    	//删除数据
    	 ((ScheduleJobMapper)mapper).deleteBatch(jobIds);
	}

    public int updateBatch(Integer[] jobIds, int status){
    	Map<String, Object> map = new HashMap<>();
    	map.put("list", jobIds);
    	map.put("status", status);
    	return  ((ScheduleJobMapper)mapper).updateBatch(map);
    }
    
	@Transactional
    public void run(Integer[] jobIds) {
    	for(Integer jobId : jobIds){
    		ScheduleUtils.run(scheduler, queryObject(jobId));
    	}
    }
	
	@Transactional
    public void runOnly(Integer jobId) {
		ScheduleUtils.run(scheduler, queryObject(jobId));
		ScheduleJob job = new ScheduleJob();
        job.setId(jobId);
        job.setStatus(ScheduleStatus.NORMAL.getValue());
        this.update(job);
    }

	@Transactional
    public void pause(Integer[] jobIds) {
        for(Integer jobId : jobIds){
    		ScheduleUtils.pauseJob(scheduler, jobId);
    	}
        
    	updateBatch(jobIds, ScheduleStatus.PAUSE.getValue());
    }
	
	@Transactional
	public void pauseOnly(Integer jobId) {
        ScheduleUtils.pauseJob(scheduler, jobId);
        ScheduleJob job = new ScheduleJob();
        job.setId(jobId);
        job.setStatus(ScheduleStatus.PAUSE.getValue());
        this.update(job);
    }

	@Transactional
    public void resume(Integer[] jobIds) {
    	for(Integer jobId : jobIds){
    		ScheduleUtils.resumeJob(scheduler, jobId);
    	}
    	updateBatch(jobIds, ScheduleStatus.NORMAL.getValue());
    }
	
	@Transactional
    public void resumeOnly(Integer jobId) {
    	ScheduleUtils.resumeJob(scheduler, jobId);
    	  ScheduleJob job = new ScheduleJob();
          job.setId(jobId);
          job.setStatus(ScheduleStatus.NORMAL.getValue());
          this.update(job);
    }
	
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap) {
		logger.info("角色查询，入参：" + paramMap);
		Page<Map<String, Object>> page = getPageMap(paramMap);
		logger.info("角色查询，入参：" + page);
		page.setRecords(((ScheduleJobMapper) mapper).selectListPage(page, paramMap));
		logger.info("角色查询，出参：" + page.toString());
		return page;
	}
	
}