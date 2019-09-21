package com.videoadmin.ying.controller;

import com.alibaba.fastjson.JSONObject;
import com.videoadmin.base.BaseController;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.utils.FastDfsUtils;
import com.videoadmin.utils.HttpCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件上传控制器
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController extends BaseController {


    @Value("${SERVERIMG_URL}")
    private String SERVERIMG_URL;

    @Value("${CARICATURE_SERVERIMG_URL}")
    private String CARICATURE_SERVERIMG_URL;

    //漫画上传类型
    private static final Integer CARICATURE_TYPE = 1;
    //其他文件上传类型
    private static final Integer OUTHER_TYPE = 2;




    public String getService() {
        return null;
    }

	/*// 上传文件(支持批量)
    @RequestMapping("/file")
    @ApiOperation(value = "上传文件")
    public Object uploadFile(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        List<String> fileNames = UploadUtil.uploadImage(request, false);
        if (fileNames.size() > 0) {
            modelMap.put("fileNames", fileNames);
            return setSuccessModelMap(modelMap);
        } else {
            setModelMap(modelMap, HttpCode.BAD_REQUEST);
            modelMap.put("msg", "请选择要上传的文件！");
            return modelMap;
        }
    }*/
    
/*	// 上传文件(支持批量)
	@RequestMapping("/image")
	@ApiOperation(value = "上传图片")
	public Object uploadImage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
	      List<String> fileNames = UploadUtil.uploadImage(request, true);
	        if (fileNames.size() > 0) {
	            modelMap.put("fileNames", fileNames);
	            return setSuccessModelMap(modelMap);
	        } else {
	            setModelMap(modelMap, HttpCode.BAD_REQUEST);
	            modelMap.put("msg", "请选择要上传的文件！");
	            return modelMap;
	        }
	}*/

	/*// 上传文件(支持批量)
	@RequestMapping("/imageData")
	@ApiOperation(value = "上传图片")
	public Object uploadImageData(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
	      List<String> fileNames = UploadUtil.uploadImageData(request);
	        if (fileNames.size() > 0) {
	            modelMap.put("fileNames", fileNames);
	            return setSuccessModelMap(modelMap);
	        } else {
	            setModelMap(modelMap, HttpCode.BAD_REQUEST);
	            modelMap.put("msg", "请选择要上传的文件！");
	            return modelMap;
	        }
	}*/

   /* *//**
     * @param request
     * @param modelMap
     * @return
     *//*
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadImage(@RequestParam MultipartFile file, HttpServletRequest request, ModelMap modelMap) {
       *//* String filePath = UploadUtil.uploadImage(file, request);
        String fileUrl = "";
        if (!"".equals(filePath)) {
            String objectId = "";
//            if(sysLogin.isPorvider()){
//            	objectId = "PROVIDER"+sysLogin.getProviderId();
//            }else{
//            	objectId = "EMPLOYEE"+sysLogin.getPublicId();
//            }
            fileUrl = UploadUtil.remove2DFS("sysUser", objectId, filePath).getReturnPath();



            return setSuccessResponse(modelMap, fileUrl);
        } else {
            return setResponse(modelMap, HttpCode.BAD_REQUEST);
        }*//*

//        String upload = fileSystemService.upload(file);

        return setSuccessResponse(modelMap, upload);

    }*/

    /**
     * @param request
     * @param modelMap
     * @return
     *//*
    @RequestMapping(value = "/CKimage", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadCKImage(@RequestParam MultipartFile upload, HttpServletRequest request, ModelMap modelMap) {
        String CKEditorFuncNum = request.getParameter("CKEditorFuncNum");
        String fileName = upload.getOriginalFilename();
        String uploadContentType = upload.getContentType();
        String expandedName = "";
        if (uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg")) {
            // IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg
            expandedName = ".jpg";
        } else if (uploadContentType.equals("image/png") || uploadContentType.equals("image/x-png")) {
            // IE6上传的png图片的headimageContentType是"image/x-png"
            expandedName = ".png";
        } else if (uploadContentType.equals("image/gif")) {
            expandedName = ".gif";
        } else if (uploadContentType.equals("image/bmp")) {
            expandedName = ".bmp";
        } else {
            String result = "";
            result = "<script type=\"text/javascript\">";
            result += "window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'',"
                    + "'文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');";
            result += "</script>";
            return result;
        }
        String filePath = UploadUtil.uploadImage(upload, request);
        String fileUrl = "";
        if (!"".equals(filePath)) {
            //SysUserLogin sysLogin = getCurrUser();
            String objectId = "";
//            if(sysLogin.isPorvider()){
//            	objectId = "PROVIDER"+sysLogin.getProviderId();
//            }else{
//            	objectId = "EMPLOYEE"+sysLogin.getPublicId();
//            }
            fileUrl = UploadUtil.remove2DFS("sysUser", objectId, filePath).getReturnPath();
            String imgPre = PropertiesUtil.getString("remote.file.uri.prefix");
            String result = "";
            result = "<script type=\"text/javascript\">";
            result += "window.parent.CKEDITOR.tools.callFunction("
                    + CKEditorFuncNum + ",'" + imgPre + fileUrl
                    + "','')";
            result += "</script>";
            return result;
        } else {
            return "";
        }
    }*/

  /*  *//**
     * 文件上传
     * @param file
     * @return
     *//*
    @RequestMapping(value = "/imageinp", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadImageByinp(@RequestParam MultipartFile file) {

            String fileId = FastDfsUtils.upload(file,CARICATURE_TYPE);

            String fileUrl = CARICATURE_SERVERIMG_URL + fileId;
            return fileUrl + "|" + fileUrl;
    }


    *//**
     * 文件上传
     * @param file
     * @return
     *//*
    @PostMapping(value = "/upload")
    @ResponseBody
    public Object upload(@RequestParam MultipartFile file) {

        String fileId = FastDfsUtils.upload(file,CARICATURE_TYPE);

        String fileUrl = CARICATURE_SERVERIMG_URL + fileId;
        return fileUrl + "|" + fileUrl;
    }*/

    /**
     * 上传漫画文件
     * @param file
     * @return
     */
    @PostMapping(value = "/uploadCaricatureFile")
    @ResponseBody
    private String uploadCaricatureFile(@RequestParam MultipartFile file){
        try {
            String fileId = FastDfsUtils.upload(file,CARICATURE_TYPE);
            String fileUrl = CARICATURE_SERVERIMG_URL + fileId;
            return fileId + "|" + fileUrl;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 上传其他文件
     * @param file
     * @return
     */
    @PostMapping(value = "/imageinp")
    @ResponseBody
    private String uploadOutherFile(@RequestParam MultipartFile file){

        try {
            String fileId = FastDfsUtils.upload(file,OUTHER_TYPE);
            String fileUrl = SERVERIMG_URL + fileId;
            return fileId + "|" + fileUrl;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

   /* *//**
     * 上传文件
     *
     * @param request
     * @param modelMap
     * @return
     *//*
    @RequestMapping(value = "/fileinp", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadFileByinp(@RequestParam MultipartFile file, HttpServletRequest request, ModelMap modelMap) {
        String filePath = UploadUtil.uploadImage(file, request);
        String realName = file.getOriginalFilename();
        String fileUrl = "";
        if (!"".equals(filePath)) {
            String objectId = "1";
            FileModel fm = UploadUtil.remove2DFS("sysUser", objectId, filePath);
            fileUrl = fm.getReturnPath();
            return fileUrl + "|" + realName;
        } else {
            return "";
        }
    }*/

    /**
     * 返回lay编辑器json数据格式
     *
     * @param success
     * @param url
     * @param message
     * @param fileName
     * @return
     */
    private String layjsonResult(boolean success, String url, String message, String fileName) {
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        if (success) {
            data.put("src", url);
            data.put("title", fileName);
            obj.put("code", 0);
            obj.put("data", data);
        } else {
            obj.put("code", 1);
            obj.put("msg", message);
            obj.put("data", data);
        }
        return obj.toJSONString();
    }

    /**
     * Description: im图片上传
     *
     * @param file
     * @param request
     * @param modelMap
     * @return
     * @see
     *//*
    @RequestMapping(value = "/im/image")
    @ResponseBody
    public Object uploadImImage(@RequestParam MultipartFile file, HttpServletRequest request, ModelMap modelMap) {
        String filePath = UploadUtil.uploadImage(file, request);
        String fileUrl = "";
        String remotUrl = "";
        if (!"".equals(file)) {
            String objectId = "im";
            fileUrl = UploadUtil.remove2DFS("sysUser", objectId, filePath).getReturnPath();

            remotUrl = PropertiesUtil.getString("remote.file.uri.prefix") + fileUrl;
            modelMap.put("code", 0);
            Map<String, String> mpp = new HashMap<String, String>();
            mpp.put("src", remotUrl);
            modelMap.put("data", mpp);
            modelMap.put("msg", "上传成功!");
        } else {
            modelMap.put("code", -1);
            modelMap.put("msg", "上传失败");
        }
        return setSuccessResponse(modelMap);
    }*/

   /* *//**
     * @param request
     * @param modelMap
     * @return
     *//*
    @RequestMapping(value = "/videoUp", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadVideo(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        String filePath1 = UploadUtil.uploadVideo(file, request);
        String[] filePath2 = filePath1.split(",");
        String filePath = filePath2[1];
        String fileUrl = "";
        if (!"".equals(filePath)) {
            String objectId = "";
//            if(sysLogin.isPorvider()){
//            	objectId = "PROVIDER"+sysLogin.getProviderId();
//            }else{
//            	objectId = "EMPLOYEE"+sysLogin.getPublicId();
//            }
            FileModel fm = UploadUtil.remove2DFS("sysUser", objectId, filePath);
            fileUrl = fm.getReturnPath();
            modelMap.put("fileUrl", fileUrl);
            return setSuccessResponse(modelMap, fileUrl);
        } else {
            return setResponse(modelMap, HttpCode.BAD_REQUEST);
        }
    }*/

  /*  *//**
     * @param request
     * @param modelMap
     * @return
     *//*
    @RequestMapping(value = "/uploadMultFile", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadMultFile(MultipartHttpServletRequest multRequest, HttpServletRequest request, ModelMap modelMap) throws Exception {
        Iterator<String> iterator = multRequest.getFileNames();
        StringBuffer sb = new StringBuffer("");
        while (iterator.hasNext()) {
            String fileName = iterator.next();
            MultipartFile file = multRequest.getFile(fileName);
            String originName = file.getOriginalFilename();
            //sb.append(originName).append(",");
            String filePath1 = UploadUtil.uploadVideo(file, request);
            String[] filePath2 = filePath1.split(",");
            String filePath = filePath2[1];
            String fileUrl = "";
            if (!"".equals(filePath)) {
                String objectId = "1";
                FileModel fm = UploadUtil.remove2DFS("sysUser", objectId, filePath);
                fileUrl = fm.getReturnPath();
//				sb.append(fileUrl).append(",").append(originName);
                sb.append(fileUrl);
            }
        }
        if (DataUtil.isNotEmpty(sb)) {
            return setSuccessResponse(modelMap, sb);
        } else {
            return setResponse(modelMap, HttpCode.BAD_REQUEST);
        }
    }
*/
    /**
     * 展示已上传文件
     *
     * @param modelMap
     * @param param
     * @return
     */
    @RequestMapping(value = "/showFile")
    @ResponseBody
    public Object showFile(ModelMap modelMap, @RequestBody(required = false) Map<String, Object> param) {
        if (DataUtil.isNotEmpty(param.get("id"))) {
            int hourId = Integer.parseInt(param.get("id").toString());
            logger.info("查询课程课时,id是:{}", hourId);
            /*Parameter parameter = new Parameter("contractInfoService","queryById").setId(hourId);
            ContractInfo hour = (ContractInfo) provider.execute(parameter).getModel();
			String file = hour.getFileUrl();
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			if(DataUtil.isNotEmpty(file)){
				String[] file1 = file.split("\\|");
				if(file1.length>0){
					for(int i =0 ;i<file1.length;i++){
						Map<String, Object> paramMap = new HashMap<String, Object>();
						String[] file2 = file1[i].split(",");
						if(file2.length>0){
							String fileUrl = file2[0];
							String fileName = file2[1];
							paramMap.put("fileUrl",fileUrl);
							paramMap.put("fileName", fileName);
						}
						list.add(paramMap);
					}
				}
			}
			if (list.size() > 0)
			{*/
//				return setSuccessResponse(modelMap);
			/*}else{
				return setResponse(modelMap, HttpCode.BAD_REQUEST);
			}*/
        }
        return setSuccessResponse(modelMap);
        //return setResponse(modelMap, HttpCode.BAD_REQUEST);
    }


    /**
     * 删除上传文件
     *
     * @param modelMap
     * @param param
     * @return
     */
    @RequestMapping(value = "/delFile")
    @ResponseBody
    public Object delFile(ModelMap modelMap, @RequestBody(required = false) Map<String, Object> param) {
        StringBuffer sb = new StringBuffer();
        String file1 = "";
        if (DataUtil.isNotEmpty(param.get("file"))) {
            String file = param.get("file").toString();
            file1 = file.substring(0, file.length() - 1);
        }
        if (DataUtil.isNotEmpty("files")) {
            String files = param.get("files").toString();
            String[] str = files.split("\\|");
            for (int i = 0; i < str.length; i++) {
                if (!file1.equals(str[i])) {
                    sb.append(str[i]).append("|");
                }
            }
        }

        if (sb.length() > 0) {
            return setSuccessResponse(modelMap, sb.toString());
        } else {
            return setResponse(modelMap, HttpCode.BAD_REQUEST);
        }
    }
}
