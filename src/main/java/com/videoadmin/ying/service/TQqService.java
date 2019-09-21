package com.videoadmin.ying.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.videoadmin.ying.dao.TPaySettingMapper;
import com.videoadmin.ying.dao.TQqMapper;
import com.videoadmin.ying.po.TPaySetting;
import com.videoadmin.ying.po.TQq;
import com.videoadmin.base.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 交流群  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tQqService")
public class TQqService extends BaseService<TQq> {
	@Autowired 
	private TQqMapper tQqMapper;
	
	public List<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("Qq数据查询，入参："+paramMap);
	    List<Map<String, Object>> list=tQqMapper.selectListPage(paramMap);
        logger.info("Qq数据查询，出参："+list);
        return list;
	}
	@Transactional
	public void addOrUpdata(Map<String, Object> param) {
	    if (param!=null)
        {
	      //更新当前表
	     TQq tQq=new TQq();
	      if (param.get("name")!=null&& !("").equals(param.get("name").toString()))
	        {
	        	tQq.setName(param.get("name").toString());
	        }
	        /*if (param.get("linkUrl")!=null&& !("").equals(param.get("linkUrl").toString()))
	        {
	        	tQq.setLinkUrl(param.get("linkUrl").toString());
	        }*/
	        if(null != param.get("id") && !"".equals(param.get("id").toString())){
				//更新
	        	tQq.setId(Integer.valueOf(param.get("id").toString()));
	        	//tQq.setUpdateBy(getCurrUser().getUserId());
	        	tQq.setUpdateTime(new Date());
	        	//更新QQ表
	            logger.info("{}更新QQ表 start...{}", tQq);
	            tQqMapper.updateById(tQq);
			}else{
				//新增
				//tQq.setCreateBy(getCurrUser().getUserId());
				tQq.setCreateTime(new Date());
				tQqMapper.insert(tQq);
			}
        }
	}
}