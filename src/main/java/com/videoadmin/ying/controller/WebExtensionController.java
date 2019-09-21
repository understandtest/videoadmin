
package com.videoadmin.ying.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.videoadmin.base.BaseController;
import com.videoadmin.ying.service.TVersionService;
@Controller
public class WebExtensionController extends BaseController{

	@Value("${SERVERIMG_URL}")
	private String root;
	
	@Autowired
	private TVersionService tVersionService;
	
	
	@RequestMapping("/share")
	public String extensionUrl(ModelMap modelMap,HttpServletRequest request){
		String extensionCode = request.getParameter("code");
		if(extensionCode != null && !"".equals(extensionCode))
		{
			modelMap.put("extensionCode", extensionCode);
			modelMap.put("codeUrl", root+"/share?code="+extensionCode);
			//获取最新的下载地址
			//IOS地址
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("versionType", 1);
			List<Map<String,Object>> iosList = new ArrayList<Map<String,Object>>();
			iosList = tVersionService.getVersionNew(params);
			if(iosList != null && iosList.size()>0)
			{
				String iosUrl = String.valueOf(iosList.get(0).get("versionUrl"));
				modelMap.put("iosUrl", iosUrl);
			}
			params.clear();
			params.put("versionType", 2);
			List<Map<String,Object>> androidList = new ArrayList<Map<String,Object>>();
			androidList = tVersionService.getVersionNew(params);
			if(androidList != null && androidList.size()>0)
			{
				String androidUrl = String.valueOf(androidList.get(0).get("versionUrl"));
				modelMap.put("androidUrl", androidUrl);
			}
		}
		return "/web/index";
	}
	
	@RequestMapping("/down")
	public Object down(ModelMap modelMap,HttpServletRequest request){
			//获取最新的下载地址
			//IOS地址
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("versionType", 1);
			List<Map<String,Object>> iosList = new ArrayList<Map<String,Object>>();
			iosList = tVersionService.getVersionNew(params);
			if(iosList != null && iosList.size()>0)
			{
				String iosUrl = String.valueOf(iosList.get(0).get("versionUrl"));
				modelMap.put("iosUrl", iosUrl);
			}
			params.clear();
			params.put("versionType", 2);
			List<Map<String,Object>> androidList = new ArrayList<Map<String,Object>>();
			androidList = tVersionService.getVersionNew(params);
			if(androidList != null && androidList.size()>0)
			{
				String androidUrl = String.valueOf(androidList.get(0).get("versionUrl"));
				modelMap.put("androidUrl", androidUrl);
			}
		return setSuccessResponse(modelMap);
	}
	
	
	
	 /**
     * 
     * Description: <br>
     * Implement: <br>
     *
     * @param request
     * @param response
     * @return
     * @throws Exception 
     * @see
     */
    @RequestMapping("/qrcodeUrl")  
    public void qrcodeUrl(HttpServletRequest request, HttpServletResponse response) {  
          try {
        	  String url = request.getParameter("url");
        	  int qrcodeWidth = 300;
              int qrcodeHeight = 300;
              String qrcodeFormat = "png";
              Map<EncodeHintType,Object> config = new HashMap<>();
              config.put(EncodeHintType.CHARACTER_SET,"UTF-8");
              config.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
              config.put(EncodeHintType.MARGIN, 0);
              BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE,qrcodeWidth,qrcodeHeight,config);
              MatrixToImageWriter.writeToStream(bitMatrix,qrcodeFormat,response.getOutputStream());
          } catch (Exception e) {
              e.printStackTrace();
        }
    }
}
