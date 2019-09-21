package com.videoadmin.ying.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.videoadmin.ying.dao.TQqMapper;
import com.videoadmin.ying.dao.TTouristMapper;
import com.videoadmin.ying.po.TQq;
import com.videoadmin.ying.po.TTourist;
import com.videoadmin.base.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 游客访问权限  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tTouristService")
public class TTouristService extends BaseService<TTourist> {
	@Autowired 
	private TTouristMapper tTouristMapper;
	
	public List<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("游客访问权限数据查询，入参："+paramMap);
	    List<Map<String, Object>> list=tTouristMapper.selectListPage(paramMap);
        logger.info("游客访问权限数据查询，出参："+list);
        return list;
	}
	@Transactional
	public void addOrUpdata(Map<String, Object> param) {
	    if (param!=null)
        {
	      //更新当前表
	     TTourist tTourist=new TTourist();
	      if (param.get("viewNum")!=null&& !("").equals(param.get("viewNum").toString()))
	        {
	    	  tTourist.setViewNum(Integer.valueOf(param.get("viewNum").toString()));
	        }
	        if (param.get("cacheNum")!=null&& !("").equals(param.get("cacheNum").toString()))
	        {
	        	tTourist.setCacheNum(Integer.valueOf(param.get("cacheNum").toString()));
	        }
	        if(null != param.get("id") && !"".equals(param.get("id").toString())){
				//更新
	        	tTourist.setId(Integer.valueOf(param.get("id").toString()));
	        	//tTourist.setUpdateBy(getCurrUser().getUserId());
	        	tTourist.setUpdateTime(new Date());
	        	//更新QQ表
	            logger.info("{}更新游客访问权限表 start...{}", tTourist);
	            tTouristMapper.updateById(tTourist);
			}else{
				//新增
				//tTourist.setCreateBy(getCurrUser().getUserId());
				tTourist.setCreateTime(new Date());
				tTouristMapper.insert(tTourist);
			}
        }
	}
}