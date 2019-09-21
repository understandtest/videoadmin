package com.videoadmin.ying.service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.SysServerMapper;
import com.videoadmin.ying.po.SysServer;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * AUTHER lbh
 * Date 2019/5/21
 */
@Service
public class SysServerService extends BaseService<SysServer> {


    /**
     * 查询所有服务器节点
     * @return
     */
    public List<SysServer> findAll(){
        return ((SysServerMapper) mapper).selectList(null);
    }

    /**
     * 根据id查询服务器具体信息
     * @param id
     * @return
     */
    public SysServer findOne(Integer id){
        logger.info("查询服务器具体信息id为:" + id);
        return ((SysServerMapper) mapper).selectById(id);
    }

    /**
     * 保存服务器信息
     * @param sysServer
     */
    public void save(SysServer sysServer){

        //有id则修改
        if(null != sysServer.getId() && !"".equals(sysServer.getId()) ){
            logger.info("修改服务器信息id为:" + sysServer.getId());
            //添加修改时间
            sysServer.setUpdateTime(new Date());
            mapper.updateById(sysServer);
        }else{
            //新增
            sysServer.setCreateTime(new Date());
            sysServer.setUpdateTime(new Date());
            mapper.insert(sysServer);
        }

    }


}
