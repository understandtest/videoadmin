package com.videoadmin.ying.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.videoadmin.utils.StringUtils;
import com.videoadmin.ying.dao.TPayOpenSettingMapper;
import com.videoadmin.ying.dao.TPaySettingMapper;
import com.videoadmin.ying.po.*;
import com.videoadmin.base.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 支付配置  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tPaySettingService")
public class TPaySettingService extends BaseService<TPaySetting> {
    @Autowired
    private TPaySettingMapper tPaySettingMapper;

    @Autowired
    private TPayOpenSettingMapper tPayOpenSettingMapper;

    @Transactional
    public void addOrUpdata(Map<String, Object> param) {
        if (param != null) {
            //更新当前表
            TPaySetting tPaySetting = new TPaySetting();
            TPaySetting tSetting = new TPaySetting();
            tPaySetting.setIsEnable(1);
            tSetting.setIsEnable(1);
            if (param.get("AlipayName") != null && !("").equals(param.get("AlipayName").toString())) {
                tPaySetting.setPayName(param.get("AlipayName").toString());
            }
            if (param.get("AlipayImgType") != null && !("").equals(param.get("AlipayImgType").toString())) {
                tPaySetting.setPayImgType(Integer.valueOf(param.get("AlipayImgType").toString()));
            }
            if (param.get("AlipayImg") != null && !("").equals(param.get("AlipayImg").toString())) {
                tPaySetting.setPayImg(param.get("AlipayImg").toString());
            }
            if (param.get("AlipayUrl") != null && !("").equals(param.get("AlipayUrl").toString())) {
                tPaySetting.setPayUrl(param.get("AlipayUrl").toString());
            }
            if (param.get("AlipayAccount") != null && !("").equals(param.get("AlipayAccount").toString())) {
                tPaySetting.setPayAccount(param.get("AlipayAccount").toString());
            }
            if (param.get("AlipayKey") != null && !("").equals(param.get("AlipayKey").toString())) {
                tPaySetting.setPayKey(param.get("AlipayKey").toString());
            }
            if (param.get("AliisEnable") != null && !("").equals(param.get("AliisEnable").toString())) {
                tPaySetting.setIsEnable(Integer.valueOf(param.get("AliisEnable").toString()));
            }
            if (param.get("AlisortNo") != null && !("").equals(param.get("AlisortNo").toString())) {
                tPaySetting.setSortNo(Integer.valueOf(param.get("AlisortNo").toString()));
            }
            if (param.get("Alidex") != null && !("").equals(param.get("Alidex").toString())) {
                tPaySetting.setDex(param.get("Alidex").toString());
            }
            if (param.get("payName") != null && !("").equals(param.get("payName").toString())) {
                tSetting.setPayName(param.get("payName").toString());
            }
            if (param.get("payImgType") != null && !("").equals(param.get("payImgType").toString())) {
                tSetting.setPayImgType(Integer.valueOf(param.get("payImgType").toString()));
            }
            if (param.get("payImg") != null && !("").equals(param.get("payImg").toString())) {
                tSetting.setPayImg(param.get("payImg").toString());
            }
            if (param.get("payUrl") != null && !("").equals(param.get("payUrl").toString())) {
                tSetting.setPayUrl(param.get("payUrl").toString());
            }
            if (param.get("payAccount") != null && !("").equals(param.get("payAccount").toString())) {
                tSetting.setPayAccount(param.get("payAccount").toString());
            }
            if (param.get("payKey") != null && !("").equals(param.get("payKey").toString())) {
                tSetting.setPayKey(param.get("payKey").toString());
            }
            if (param.get("isEnable") != null && !("").equals(param.get("isEnable").toString())) {
                tPaySetting.setIsEnable(Integer.valueOf(param.get("isEnable").toString()));
            }
            if (param.get("sortNo") != null && !("").equals(param.get("sortNo").toString())) {
                tSetting.setSortNo(Integer.valueOf(param.get("sortNo").toString()));
            }
            if (param.get("dex") != null && !("").equals(param.get("dex").toString())) {
                tSetting.setDex(param.get("dex").toString());
            }
            if (null != param.get("Aliid") && !"".equals(param.get("Aliid").toString())) {
                //更新
                tPaySetting.setId(Integer.valueOf(param.get("Aliid").toString()));
                //tPaySetting.setUpdateBy(getCurrUser().getUserId());
                tPaySetting.setUpdateTime(new Date());
                //更新支付表
                logger.info("{}更新支付表 start...{}", tPaySetting);
                tPaySettingMapper.updateById(tPaySetting);
            } else {
                //新增
                tPaySetting.setPayType(1);
                //tPaySetting.setCreateBy(getCurrUser().getUserId());
                tPaySetting.setCreateTime(new Date());
                tPaySettingMapper.insert(tPaySetting);
            }


            if (null != param.get("id") && !"".equals(param.get("id").toString())) {
                //更新
                tSetting.setId(Integer.valueOf(param.get("id").toString()));
                //tSetting.setUpdateBy(getCurrUser().getUserId());
                tSetting.setUpdateTime(new Date());
                //更新支付表
                logger.info("{}更新支付表 start...{}", tSetting);
                tPaySettingMapper.updateById(tSetting);
            } else {
                //新增
                tSetting.setPayType(2);
                //tSetting.setCreateBy(getCurrUser().getUserId());
                tSetting.setCreateTime(new Date());
                tPaySettingMapper.insert(tSetting);
            }

            TPayOpenSetting payOpenSetting = new TPayOpenSetting();
            if (param.get("isOpenPay") != null && !("").equals(param.get("isOpenPay").toString())) {
                payOpenSetting.setIsOpen(param.get("isOpenPay").toString());
            }

            if(param.get("openSettingId") != null && !("").equals(param.get("openSettingId").toString())){
                payOpenSetting.setId(Integer.parseInt(param.get("openSettingId").toString()));
                tPayOpenSettingMapper.updateById(payOpenSetting);
            }else{
                payOpenSetting.setId(1);
                tPayOpenSettingMapper.insert(payOpenSetting);
            }

        }
    }

    public List<TPaySetting> queryPaySetting() {
        return ((TPaySettingMapper) mapper).queryPaySetting();
    }
}