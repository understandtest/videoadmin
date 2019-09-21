package com.videoadmin.ying.service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.CaricatureBookcaseMapper;
import com.videoadmin.ying.po.CaricatureBookcase;
import org.springframework.stereotype.Service;


/**
 * Author lbh
 * Date 2019-07-27
 */
@Service
public class CaricatureBookcaseService extends BaseService<CaricatureBookcase> {


    /**
     * 删除我的书架，根据漫画id
     * @param caricatureId
     */
    public void delByCaricatureId(int caricatureId) {
        ((CaricatureBookcaseMapper)mapper).delByCaricatureId(caricatureId);
    }
}