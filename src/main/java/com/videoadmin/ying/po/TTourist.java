package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 游客访问权限
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@TableName("t_tourist")
@SuppressWarnings("serial")
public class TTourist extends BaseModel {

      /**
     * 观影次数
     */
         	   @TableField("view_num")
        	private Integer viewNum;
      /**
     * 缓存次数
     */
         	   @TableField("cache_num")
        	private Integer cacheNum;


	public Integer getViewNum() {
		return viewNum;
	}

	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
	}

	public Integer getCacheNum() {
		return cacheNum;
	}

	public void setCacheNum(Integer cacheNum) {
		this.cacheNum = cacheNum;
	}

}