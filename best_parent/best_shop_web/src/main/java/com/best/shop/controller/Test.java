package com.best.shop.controller;

import com.best.shop.utils.FastDFSClient;
import org.csource.fastdfs.StorageClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] arg) {
        //获取文件的字节数组名

        // String originalFileName = uploadFile.getOriginalFilename();
        //String extName = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

        // 2.获取文件的字节数组
        // byte[] bytes = uploadFile.getBytes();
        FastDFSClient client = null;

        String[] path = null;

        {
            try {
                client = new FastDFSClient("E:\\javaEE\\best_parent\\best_shop_web\\src\\main\\resources\\config\\fdfs_client.conf");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        StorageClient storageClient = new StorageClient(client.getTrackerServer(), client.getStorageServer());



        try {
            path = storageClient.upload_file("E:\\123.png", "png", null);
        } catch (Exception e) {
            e.printStackTrace();


        }
        for(String string:path){
            System.out.println(string);
        }
    }
}
