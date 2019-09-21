package com.videoadmin.ying.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.videoadmin.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 用户协议表
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@TableName("t_protocol")
@SuppressWarnings("serial")
public class TProtocol extends BaseModel {

      /**
     * 用户协议
     */
         	   @TableField("protocol_text")
        	private String protocolText;


	public String getProtocolText() {
		return protocolText;
	}

	public void setProtocolText(String protocolText) {
		this.protocolText = protocolText;
	}

}