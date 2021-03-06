package com.videoadmin.utils;

import com.videoadmin.utils.decode.BASE64Decoder;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * 上传辅助类 与Spring.multipartResolver冲突
 * 
 * @author ShenHuaJie
 */
public final class UploadUtil {
    private UploadUtil() {
    }

    private static final Logger logger = LogManager.getLogger();

    /** 上传文件缓存大小限制 */
    private static int fileSizeThreshold = 1024 * 1024 * 1;
    /** 上传文件临时目录 */
    private static final String uploadFileDir = "/WEB-INF/upload/";

    /** 获取所有文本域 */
    public static final List<?> getFileItemList(HttpServletRequest request, File saveDir) throws FileUploadException {
        if (!saveDir.isDirectory()) {
            saveDir.mkdir();
        }
        List<?> fileItems = null;
        RequestContext requestContext = new ServletRequestContext(request);
        if (FileUpload.isMultipartContent(requestContext)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(saveDir);
            factory.setSizeThreshold(fileSizeThreshold);
            ServletFileUpload upload = new ServletFileUpload(factory);
            fileItems = upload.parseRequest(request);
        }
        return fileItems;
    }

    /** 获取文本域 */
    public static final FileItem[] getFileItem(HttpServletRequest request, File saveDir, String... fieldName)
        throws FileUploadException {
        if (fieldName == null || saveDir == null) {
            return null;
        }
        List<?> fileItemList = getFileItemList(request, saveDir);
        FileItem fileItem = null;
        FileItem[] fileItems = new FileItem[fieldName.length];
        for (int i = 0; i < fieldName.length; i++) {
            for (Iterator<?> iterator = fileItemList.iterator(); iterator.hasNext();) {
                fileItem = (FileItem)iterator.next();
                // 根据名字获得文本域
                if (fieldName[i] != null && fieldName[i].equals(fileItem.getFieldName())) {
                    fileItems[i] = fileItem;
                    break;
                }
            }
        }
        return fileItems;
    }

    /** 上传文件处理(支持批量) */
    public static List<String> uploadImage(HttpServletRequest request, boolean lessen) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        List<String> fileNames = InstanceUtil.newArrayList();
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            String pathDir = getUploadDir(request);
            File dirFile = new File(pathDir);
            if (!dirFile.isDirectory()) {
                dirFile.mkdirs();
            }
            for (Iterator<String> iterator = multiRequest.getFileNames(); iterator.hasNext();) {
                String key = iterator.next();
                MultipartFile multipartFile = multiRequest.getFile(key);
                if (multipartFile != null) {
                    String name = multipartFile.getOriginalFilename();
                    if (name.indexOf(".") == -1 && "blob".equals(name)) {
                        name = name + ".png";
                    }
                    String uuid = UUID.randomUUID().toString();
                    String postFix = name.substring(name.lastIndexOf(".")).toLowerCase();
                    String fileName = uuid + postFix;
                    String filePath = pathDir + File.separator + fileName;
                    File file = new File(filePath);
                    file.setWritable(true, false);
                    try {
                        multipartFile.transferTo(file);
                        fileNames.add(fileName);
                    } catch (Exception e) {
                        logger.error(name + "保存失败", e);
                    }
                    if (lessen) {
                        try { // 缩放
                            BufferedImage bufferedImg = ImageIO.read(file);
                            int orgwidth = bufferedImg.getWidth();// 原始宽度
                            ImageUtil.scaleWidth(file, 100);
                            if (orgwidth > 300) {
                                ImageUtil.scaleWidth(file, 300);
                            }
                            if (orgwidth > 500) {
                                ImageUtil.scaleWidth(file, 500);
                            }
                        } catch (Exception e) {
                            logger.error(name + "缩放失败", e);
                        }
                    }
                }
            }
        }
        return fileNames;
    }
    
    public static List<String> uploadImageData(HttpServletRequest request) {
        List<String> fileNames = InstanceUtil.newArrayList();
        Enumeration<String> params = request.getParameterNames();
        String pathDir = getUploadDir(request);
        File dir = new File(pathDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        while (params.hasMoreElements()) {
            String key = params.nextElement();
            String fileStr = request.getParameter(key);
            if (fileStr != null && !"".equals(fileStr)) {
                int index = fileStr.indexOf("base64");
                if (index > 0) {
                    try {
                        String fileName = UUID.randomUUID().toString();
                        String preStr = fileStr.substring(0, index + 7);
                        String prefix = preStr.substring(preStr.indexOf("/") + 1, preStr.indexOf(";")).toLowerCase();
                        fileStr = fileStr.substring(fileStr.indexOf(",") + 1);
                        BASE64Decoder decoder = new BASE64Decoder();
                        byte[] bb = decoder.decodeBuffer(fileStr);
                        for (int j = 0; j < bb.length; ++j) {
                            if (bb[j] < 0) {// 调整异常数据
                                bb[j] += 256;
                            }
                        }
                        String distPath = pathDir + fileName + "." + prefix;
                        OutputStream out = new FileOutputStream(distPath);
                        out.write(bb);
                        out.flush();
                        out.close();
                        fileNames.add(fileName + "." + prefix);
                    } catch (Exception e) {
                        logger.error("上传文件异常：", e);
                    }
                }
            }
        }
        return fileNames;
    }
    public static String uploadImage(MultipartFile file,HttpServletRequest request) {
    	String pathDir = getUploadDir(request);
    	String distPath = "";
    	File dir = new File(pathDir);
    	if (!dir.exists()) {
    		dir.mkdirs();
    	}
    	if(!file.isEmpty()){
    		try {
	    		if (file.getBytes() != null && !"".equals(file.getBytes())) {
	    				String realName = file.getOriginalFilename();
						String fileName = UUID.randomUUID().toString();
						byte[] bb = file.getBytes();
						for (int j = 0; j < bb.length; ++j) {
							if (bb[j] < 0) {// 调整异常数据
								bb[j] += 256;
							}
						}
						distPath = pathDir +fileName+ realName.substring(realName.lastIndexOf("."));
						OutputStream out = new FileOutputStream(distPath);
						out.write(bb);
						out.flush();
						out.close();
	    		}
			} catch (Exception e) {
				logger.error("上传文件异常：", e);
			}
    	}
    	return distPath;
    }

    public static String uploadVideo(MultipartFile file,HttpServletRequest request) {
    	String pathDir = getUploadDir(request);
    	RandomAccessFile raFile = null;
    	BufferedInputStream inputStream=null;
    	//File f = (File)file;
    	String returnValue = null;
    	String distPath = "";
    	File dir = new File(pathDir);
    	if (!dir.exists()) {
    		dir.mkdirs();
    	}
    	if(!file.isEmpty()){
    		try {
    			String realName = file.getOriginalFilename();
				String fileName = UUID.randomUUID().toString();
    			distPath = pathDir +fileName+ realName.substring(realName.lastIndexOf("."));
    			File dirFile = new File(distPath);
    			//以读写的方式打开目标文件
    			raFile = new RandomAccessFile(dirFile, "rw"); 
    			raFile.seek(raFile.length());
    			inputStream = new BufferedInputStream(file.getInputStream());
    			byte[] buf = new byte[1024];
    			int length = 0;
    			while ((length = inputStream.read(buf)) != -1) {
    			  raFile.write(buf, 0, length);
    			}
			} catch (Exception e) {
				returnValue = "error";
				 e.printStackTrace();  
				logger.error("上传文件异常：", e);
			}finally{
				try {  
		             if (inputStream != null) {  
		                 inputStream.close();  
		             }  
		             if (raFile != null) {  
		                 raFile.close();  
		             }  
		         }catch(Exception e){  
		        	 e.printStackTrace();  
		         }  
			}
    	}
    	return returnValue+","+distPath;
    }
    

    /**
     * Base64上传
     * Description: <br>
     * Implement: <br>
     *
     * @param base64
     * @param request
     * @return 
     * @see
     */
    public static String uploadBase64Image(String base64,HttpServletRequest request) {
        BASE64Decoder decoder = new BASE64Decoder();  
        String pathDir = getUploadDir(request);
        String distPath = "";
        File dir = new File(pathDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if(base64 != null && !"".equals(base64)){
            try {
                byte[] bb = decoder.decodeBuffer(base64); 
                String end = ImageUtil.getFileExtendName(bb);
                String fileName = UUID.randomUUID().toString();
                for (int j = 0; j < bb.length; ++j) {
                    if (bb[j] < 0) {// 调整异常数据
                        bb[j] += 256;
                    }
                }
                distPath = pathDir +fileName+ end;
                OutputStream out = new FileOutputStream(distPath);
                out.write(bb);
                out.flush();
                out.close();
            } catch (Exception e) {
                logger.error("上传文件异常：", e);
            }
        }
        return distPath;
    }
    
    /**
     * Base64上传
     * Description: <br>
     * Implement: <br>
     *
     * @param base64
     * @param request
     * @return 
     * @see
     */
    public static String uploadBase64Image2(String base64,String type,HttpServletRequest request) {
        BASE64Decoder decoder = new BASE64Decoder();  
        String pathDir = getUploadDir(request);
        String distPath = "";
        File dir = new File(pathDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if(base64 != null && !"".equals(base64)){
            try {
                byte[] bb = decoder.decodeBuffer(base64); 
                String end = "";
                if(type != null && !"".equals(type))
                {
                    end ="."+type;
                }
                else
                {
                    end =ImageUtil.getFileExtendName(bb);
                }
                String fileName = UUID.randomUUID().toString();
                for (int j = 0; j < bb.length; ++j) {
                    if (bb[j] < 0) {// 调整异常数据
                        bb[j] += 256;
                    }
                }
                distPath = pathDir +fileName+ end;
                OutputStream out = new FileOutputStream(distPath);
                out.write(bb);
                out.flush();
                out.close();
            } catch (Exception e) {
                logger.error("上传文件异常：", e);
            }
        }
        return distPath;
    }


    /** 获取上传文件临时目录 */
    public static String getUploadDir(HttpServletRequest request) {
        return request.getServletContext().getRealPath(uploadFileDir) + File.separator;
    }


}
