package com.videoadmin.ying.service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.SysSmsMapper;
import com.videoadmin.ying.po.SysSms;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 服务实现�?
 * </p>
 *
 * @author haha
 * @since 2019-03-24
 */
@Service("sysSmsService")
public class SysSmsService extends BaseService<SysSms> {


    public Map<String, Object> selectSms() {
        return ((SysSmsMapper) mapper).selectSms();
    }


    @Transactional
    public void updateSms(SysSms sysSms) {

        //id为空设置数据
        if(null == sysSms.getId()){
            sysSms.setId(1);
        }

        mapper.updateById(sysSms);

    }
}