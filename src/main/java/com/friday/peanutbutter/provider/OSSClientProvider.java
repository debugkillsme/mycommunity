package com.friday.peanutbutter.provider;

import com.aliyun.oss.*;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.friday.peanutbutter.exception.CustomizeErrorCode;
import com.friday.peanutbutter.exception.CustomizeException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class OSSClientProvider {
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    @Value("${OSSClient.accessKey}")
    private String accessKeyId;

    @Value("${OSSClient.endPoint}")
    // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    private String endpoint;

    @Value("${OSSClient.accessKeySecret}")
    private String accessKeySecret;
    @Value("${OSSClient.bucketName}")
    private String bucketName;

    public String upload(FileInputStream filePath,String fileName){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String generatedFileName = null;
        String[] filePaths = fileName.split("\\.");
        if(filePaths.length>1){
            generatedFileName = UUID.randomUUID().toString()+"."+filePaths[filePaths.length-1];
        }
        try {
            // 创建PutObject请求,上传图片
            ossClient.putObject(bucketName, generatedFileName, filePath);

            //获得预览图片的链接
            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName,generatedFileName, HttpMethod.GET);
            //设置过期时间
            Date date = new Date(System.currentTimeMillis()+(long)Math.pow(10,10));
            urlRequest.setExpiration(date);
            //获取图片链接
            URL url = ossClient.generatePresignedUrl(urlRequest);
            if(url!=null){
                ossClient.shutdown();
                return url.toString();
            }else {
                //获取图片失败
                throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAILED);
            }

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        //但是图片链接过一段时间就会过期
        OSSObject ossObject = ossClient.getObject(bucketName, generatedFileName);


        // 关闭OSSClient。
        ossClient.shutdown();
        return "";
    }
}
