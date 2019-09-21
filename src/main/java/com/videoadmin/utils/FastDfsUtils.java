package com.videoadmin.utils;

import org.csource.fastdfs.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author hong
 * @Date 19-8-20
 */
public class FastDfsUtils {


    /**
     * 将文件上传到fdfs分布式文件系统
     *
     * @param multipartFile 文件本身
     * @return 文件id
     */
    public static String upload(MultipartFile multipartFile, Integer type) {

        //初始化fdfs配置文件参数
        try {

            initParam(type);

            TrackerClient trackerClient = new TrackerClient();

            TrackerServer trackerServer = trackerClient.getConnection();

            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);

            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);

            //获取文件后缀
            String fileName = multipartFile.getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
            //文件上传
            String fileId = storageClient1.upload_file1(multipartFile.getBytes(), ext, null);

            return fileId;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 初始化配置
     * @param type 1:漫画文件上传
     *             2:其他文件上传
     */
    private static void initParam(Integer type) {

        try {

            switch (type) {
                case 1: {
                    ClientGlobal.initByProperties("caricature_fastdfs.properties");
                    break;
                }
                case 2: {
                    ClientGlobal.initByProperties("fastdfs.properties");
                    break;
                }
                default:
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
