

package com.videoadmin.utils;

import java.util.Map;


public class SysConstants {
    public static final String Exception_Head = "抱歉！系统发生了异常，请联系管理员:";

    public static String AUTHORIZATION = "Token"; //客户端定义标识头部的Token

    public static String MD5_SALT = "video"; //MD5加盐

    public static String USER_ACTIVITY_INFO = "USER_ACTIVITY";  //Session标识

    /**
     * 当前用户
     */
    public static final String CURRENT_USER = "CURRENT_USER";

    /**
     * 缓存命名空间
     */
    public static final String CACHE_NAMESPACE = "Ying:";

    /**
     * 缓存键值
     */
    public static final Map<Class<?>, String> cacheKeyMap = InstanceUtil.newHashMap();

    /**
     * 验证码
     */
    public static final String VERICODE_KEY = "VERICODE_KEY";

    /**
     * 客户端主题
     */
    public static final String WEBTHEME = "webYing";

    /**
     * 登录用户数量
     */
    public static final String USER_NUMBER = "USER_NUMBER";

    /**
     * 在线用户数量
     */
    public static final String ALLUSER_NUMBER = "SYSTEM:" + CACHE_NAMESPACE + "ALLUSER_NUMBER";


    /**
     * 云存储配置KEY
     */
    public final static String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";


    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        private CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
