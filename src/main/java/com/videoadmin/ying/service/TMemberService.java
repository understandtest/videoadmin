package com.videoadmin.ying.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.MD5Util;
import com.videoadmin.ying.dao.TMemberLoginMapper;
import com.videoadmin.ying.dao.TMemberMapper;
import com.videoadmin.ying.po.TMember;
import com.videoadmin.ying.po.TMemberLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 会员信息  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tMemberService")
public class TMemberService extends BaseService<TMember> {
    @Autowired
    private TMemberMapper tMemberMapper;
    @Autowired
    private TMemberLoginMapper tMemberLoginMapper;

    public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap) {
        logger.info("会员查询，入参：" + paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("会员查询，入参：" + page);
        page.setRecords(((TMemberMapper) mapper).selectListPage(page, paramMap));
//        paramMap.put("pageSize",page.getSize());
//        paramMap.put("pageNum",(page.getCurrent() - 1) * page.getSize());
//        page.setRecords(((TMemberMapper) mapper).selectListPage(paramMap));
        logger.info("会员查询，出参：" + page.toString());

        // 查询总记录数
//        long total = ((TMemberMapper) mapper).countListPage(paramMap);
//        page.setTotal((int)total);

        return page;
    }

    public Map<String, Object> getMemberInfo(Map<String, Object> paramMap) {
        logger.info("开始查询会员信息");
        return ((TMemberMapper) mapper).getMemberInfo(paramMap);
    }

    @Transactional
    public void addOrUpdata(Map<String, Object> param) {
        if (param != null) {
            //更新当前表
            TMember tMember = new TMember();
            TMemberLogin tMemberLogin = new TMemberLogin();
            if (param.get("headpic") != null && !("").equals(param.get("headpic").toString())) {
                tMember.setHeadpic(param.get("headpic").toString());
            }
            if (param.get("nickName") != null && !("").equals(param.get("nickName").toString())) {
                tMember.setNickName(param.get("nickName").toString());
            }
            if (param.get("tel") != null && !("").equals(param.get("tel").toString())) {
                tMember.setTel(param.get("tel").toString());
                tMemberLogin.setTel(param.get("tel").toString());
            }
            if (param.get("levelId") != null && !("").equals(param.get("levelId").toString())) {

                tMember.setLevelId(Integer.valueOf(param.get("levelId").toString()));

            }
            if (param.get("integralNumber") != null && !("").equals(param.get("integralNumber").toString())) {
                tMember.setIntegralNumber(Integer.valueOf(param.get("integralNumber").toString()));
            }


            if (param.get("viewNum") != null && !("").equals(param.get("viewNum").toString())) {
                tMember.setViewNum(Integer.valueOf(param.get("viewNum").toString()));
            }
            if (param.get("usedViewNum") != null && !("").equals(param.get("usedViewNum").toString())) {
                tMember.setUsedViewNum(Integer.valueOf(param.get("usedViewNum").toString()));
            }
            if (param.get("cacheNum") != null && !("").equals(param.get("cacheNum").toString())) {
                tMember.setCacheNum(Integer.valueOf(param.get("cacheNum").toString()));
            }
            if (param.get("usedCacheNum") != null && !("").equals(param.get("usedCacheNum").toString())) {
                tMember.setUsedCacheNum(Integer.valueOf(param.get("usedCacheNum").toString()));
            }
            if (param.get("vipDate") != null && !("").equals(param.get("vipDate").toString())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = sdf.parse(param.get("vipDate").toString());
                    tMember.setVipDate(date);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (param.get("extensionCode") != null && !("").equals(param.get("extensionCode").toString())) {
                tMember.setExtensionCode(param.get("extensionCode").toString());
            }

            if (param.get("isVip") != null && !("").equals(param.get("isVip").toString())) {
                tMember.setIsVip(Integer.valueOf(param.get("isVip").toString()));
            }
            if (param.get("isRemind") != null && !("").equals(param.get("isRemind").toString())) {
                tMember.setIsRemind(Integer.valueOf(param.get("isRemind").toString()));
            }
            if (param.get("vipId") != null && !("").equals(param.get("vipId").toString())) {
                tMember.setVipId(Integer.valueOf(param.get("vipId").toString()));
            }
            if (param.get("isDeviceEnable") != null && !("").equals(param.get("isDeviceEnable").toString())) {
                tMemberLogin.setIsDeviceEnable(Integer.valueOf(param.get("isDeviceEnable").toString()));
            }
            if (param.get("isEnable") != null && !("").equals(param.get("isEnable").toString())) {
                tMemberLogin.setIsEnable(Integer.valueOf(param.get("isEnable").toString()));
            }
            if (param.get("deviceCode") != null && !("").equals(param.get("deviceCode").toString())) {
                tMemberLogin.setDeviceCode(param.get("deviceCode").toString());
            }
            if (param.get("fromCode") != null && !("").equals(param.get("fromCode").toString())) {
                tMemberLogin.setFromCode(param.get("fromCode").toString());
            }
            if (param.get("cronNum") != null && !("").equals(param.get("cronNum").toString())) {
                tMember.setCronNum(Integer.valueOf(param.get("cronNum").toString()));
            }
            if (param.get("pwd") != null && !("").equals(param.get("pwd").toString())) {

                String pwd = param.get("pwd").toString();

                if(pwd.length() != 32){
                    //重新加密
                    pwd = MD5Util.md5(pwd, "ying");
                }
                tMemberLogin.setPwd(pwd);
            }

            if (null != param.get("id") && !"".equals(param.get("id").toString())) {
                //更新
                tMember.setId(Integer.valueOf(param.get("id").toString()));
                //tMember.setUpdateBy(getCurrUser().getUserId());
                tMember.setUpdateTime(new Date());
                //更新视频表
                logger.info("{}更新用户表 start...{}", tMember);
                tMemberMapper.updateById(tMember);
            } else {
                //新增
                //tMember.setCreateBy(getCurrUser().getUserId());
                tMember.setCreateTime(new Date());

                tMemberLogin.setFromCode("后台添加"); //渠道

                String extensionCode = getExtensionCode(); //推广码
                tMember.setExtensionCode(extensionCode);

                tMemberLogin.setUpdateTime(new Date()); //更新时间

                tMemberMapper.insert(tMember);
            }
            if (tMember != null) {
                tMemberLogin.setMemberId(tMember.getId());
            }
            if (null != param.get("loginId") && !"".equals(param.get("loginId").toString())) {
                //更新
                tMemberLogin.setId(Integer.valueOf(param.get("loginId").toString()));
                //tMemberLogin.setUpdateBy(getCurrUser().getUserId());
                tMemberLogin.setUpdateTime(new Date());
                //更新视频表
                logger.info("{}更新用户表 start...{}", tMemberLogin);

                tMemberLoginMapper.updateById(tMemberLogin);
            } else {
                //新增
                //tMemberLogin.setCreateBy(getCurrUser().getUserId());
                tMemberLogin.setCreateTime(new Date());
                tMemberLoginMapper.insert(tMemberLogin);
            }

        }
    }


    private String getExtensionCode() {
        //创建推广码
        String extensionCode = DataUtil.createExtensionCode();
        TMember tMember2 = new TMember();
        tMember2.setExtensionCode(extensionCode);
        tMember2 = this.selectOne(tMember2);
        if (tMember2 == null || "".equals(tMember2)) {
            return extensionCode;
        }
        return getExtensionCode();
    }
}