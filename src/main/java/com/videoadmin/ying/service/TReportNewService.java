package com.videoadmin.ying.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.utils.DateUtil;
import com.videoadmin.utils.StringUtils;
import com.videoadmin.ying.dao.*;
import com.videoadmin.ying.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * <p>
 * 服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-11-01
 */
@Service("tReportNewService")
public class TReportNewService extends BaseService<TReportNew> {


    private static final String IOS_EQUIPMENT = "ios"; // ios设备
    private static final String ANDROID_EQUIPMENT = "android"; // 安卓设备

    private static final int PAY_SUCCESS = 2; //支付成功



    @Autowired
    private TDateMapper dateMapper;

    @Autowired
    private TMemberMapper memberMapper;

    @Autowired
    private TVideoPayMapper videoPayMapper;

    @Autowired
    private TExtensionHistoryMapper extensionHistoryMapper;

    @Autowired
    private TVipHistoryMapper vipHistoryMapper;

    @Autowired
    private TMemberLoginMapper memberLoginMapper;


    /**
     * 分页查询统计
     *
     * @param paramMap
     * @return
     */
    public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap) {
        logger.info("统计记录查询，入参：" + paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("统计记录查询，入参：" + page);

        //分页查询日期
        List<TDate> dateList = dateMapper.selectPage(page, new EntityWrapper<TDate>().orderBy("date",false));

        long start = System.currentTimeMillis();

        List<Map<String, Object>> statisticsList = handledStatistics(dateList);

        long end = System.currentTimeMillis();

        logger.info("统计时长为{}秒",(end-start)/1000.0);

        page.setRecords(statisticsList);
        logger.info("统计记录查询，出参：" + page.toString());
        return page;
    }

    /**
     * 处理统计数据
     *
     * @param dateList
     * @return
     */
    private List<Map<String, Object>> handledStatistics(List<TDate> dateList) {

        List<Map<String, Object>> statisticsList = new ArrayList<>();

        // 公共用户数据
        List<TMember> memberList = memberMapper.selectList(null); // 用户集合
        List<TMemberLogin> memberLoginList = memberLoginMapper.selectList(null); // 用户登录集合
        List<TVideoPay> videoPayList = videoPayMapper.selectList(null); //视频播放集合
        List<TExtensionHistory> extensionHistoryList = extensionHistoryMapper.selectList(null); // 推广人数集合
        List<TVipHistory> vipHistoryList = vipHistoryMapper.selectList(null); // 充值集合

        dateList.forEach((date) -> {

            Map<String, Object> statisticsMap = new HashMap<>();

            // 格式化日期为字符串
            String dateStr = DateUtil.format(date.getDate());

            // 获取新用户人数
            long newUserNumber = getNewUserNumber(dateStr, memberList);
            // 获取绑定用户人数
            long bindNumber = getBindNumber(dateStr, memberList);
            // 获取活跃人数
            long briskNumber = getBriskNumber(dateStr, videoPayList,1);

            // 查询安卓人数
            long androidNumber = getEquipmentNumber(ANDROID_EQUIPMENT,memberLoginList,dateStr);
            // 查询ios人数
            long iosNumber = getEquipmentNumber(IOS_EQUIPMENT,memberLoginList,dateStr);
            // 推广人数
            long extensionNumber = getExtensionNumber(dateStr,extensionHistoryList);
            // 充值人数
            long payNumber = getPayNumber(dateStr,vipHistoryList);
            // 获取充值金额
            double sumPrice = getSumPrice(dateStr,vipHistoryList);



            // 存入数据
            statisticsMap.put("date", dateStr);
            statisticsMap.put("newUserNumber", newUserNumber);
            statisticsMap.put("bindNumber", bindNumber);
            statisticsMap.put("briskNumber", briskNumber);
            statisticsMap.put("androidNumber", androidNumber);
            statisticsMap.put("iosNumber", iosNumber);
            statisticsMap.put("extensionNumber", extensionNumber);
            statisticsMap.put("payNumber", payNumber);
            statisticsMap.put("sumPrice", sumPrice);

            // 添加到统计集合中
            statisticsList.add(statisticsMap);
        });

        return statisticsList;
    }

    /**
     * 获取充值金额
     * @param dateStr 时间
     * @param vipHistoryList vip充值记录
     * @return
     */
    private double getSumPrice(String dateStr, List<TVipHistory> vipHistoryList) {

        OptionalDouble optionalDouble = vipHistoryList.parallelStream().filter(vh -> {
            String createDateStr = DateUtil.format(vh.getCreateTime());
            // 过滤当天支付成功的数据
            return dateStr.equals(createDateStr) && vh.getPayStatus() == PAY_SUCCESS;
        }).mapToDouble(TVipHistory::getPayPrice).reduce(Double::sum);

        return optionalDouble.isPresent() ? optionalDouble.getAsDouble() : 0;
    }

    /**
     * 获取充值人数数量
     * @param dateStr 日期
     * @param vipHistoryList 充值历史记录集合
     * @return
     */
    private long getPayNumber(String dateStr, List<TVipHistory> vipHistoryList) {

        ArrayList<TVipHistory> collect = vipHistoryList.parallelStream().filter(vh -> {
            String createDateStr = DateUtil.format(vh.getCreateTime());
            // 过滤当天支付成功的数据
            return dateStr.equals(createDateStr) && vh.getPayStatus() == PAY_SUCCESS;
        }).collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(TVipHistory::getMemberId))), ArrayList::new));

        return collect.size();
    }

    /**
     * 获取推广人数
     * @param dateStr 日期
     * @param extensionHistoryList 推人列表
     * @return
     */
    private long getExtensionNumber(String dateStr, List<TExtensionHistory> extensionHistoryList) {

        return extensionHistoryList.parallelStream().filter(eh -> {
            String createDateStr = DateUtil.format(eh.getCreateTime());
            return dateStr.equals(createDateStr);
        }).count();

    }

    /**
     * 查询设备数量
     * @param equipmentType 设备类型
     * @param memberLoginList 用户登录数据列表
     * @param dateStr 日期
     * @return
     */
    private long getEquipmentNumber(String equipmentType, List<TMemberLogin> memberLoginList, String dateStr) {

        return memberLoginList.parallelStream().filter(ml ->{
            String createDateStr = DateUtil.format(ml.getCreateTime());
            return dateStr.equals(createDateStr) && equipmentType.equals(ml.getFromCode());
        }).count();
    }

    /**
     * 获取活跃人数
     *
     * @param dateStr     时间
     * @param videoPayList
     * @param videoNumber 观看影片数量（大于等于影片数量算活跃）
     * @return
     */
    private long getBriskNumber(String dateStr, List<TVideoPay> videoPayList, int videoNumber) {

        // 过滤时间后的数据
        List<TVideoPay> filterDateVideoPayList = videoPayList.parallelStream().filter((vp) -> {
            String createDateStr = DateUtil.format(vp.getCreateTime());
            return dateStr.equals(createDateStr);
        }).collect(Collectors.toList());

        // 查询当前播放过视频的用户
        List<TVideoPay> distVideoPayList = filterDateVideoPayList.parallelStream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(TVideoPay::getMemberId))),
                ArrayList::new));

        // 过滤影片观看数量小于影片书的用户
        return distVideoPayList.parallelStream().mapToLong(vp -> filterDateVideoPayList.parallelStream().filter(v -> vp.getMemberId().intValue() == v.getMemberId()).count()).filter(count -> count > videoNumber).count();
    }

    /**
     * 获取绑定手机号用户
     *
     * @param dateStr    日期
     * @param memberList 用户集合
     * @return
     */
    private long getBindNumber(String dateStr, List<TMember> memberList) {

        return memberList.parallelStream().filter(m -> {
            String createDateStr = DateUtil.format(m.getCreateTime());
            return dateStr.equals(createDateStr) && StringUtils.isNotEmpty(m.getTel());
        }).count();

    }

    /**
     * 根据日期统计新用户人数
     *
     * @param dateStr    日期
     * @param memberList 用户集合
     * @return
     */
    private long getNewUserNumber(String dateStr, List<TMember> memberList) {

        return memberList.parallelStream().filter(m -> {
            String createDateStr = DateUtil.format(m.getCreateTime());
            // 比较创建日期并且手机号不为空，则为新用户
            return dateStr.equals(createDateStr) && StringUtils.isEmpty(m.getTel());
        }).count();

    }


}