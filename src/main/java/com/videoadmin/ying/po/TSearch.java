package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 搜索关键词
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@TableName("t_search")
@SuppressWarnings("serial")
public class TSearch extends BaseModel {

      /**
     * 搜索关键字
     */
         	   @TableField("search_name")
        	private String searchName;
      /**
     * 搜索次数
     */
         	   @TableField("search_num")
        	private Integer searchNum;


	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Integer getSearchNum() {
		return searchNum;
	}

	public void setSearchNum(Integer searchNum) {
		this.searchNum = searchNum;
	}

}