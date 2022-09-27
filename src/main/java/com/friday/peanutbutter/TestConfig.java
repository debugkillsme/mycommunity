package com.friday.peanutbutter;


import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestConfig {
    @Autowired
    private StringEncryptor stringEncryptor;

    @Value("${OSSClient.accessKey}")
    private String OSSAK;

    @Value("${OSSClient.accessKeySecret}")
    private String OSSKEY;

    @Value("${github.client.id}")
    private String githubID;

    @Value("${github.client.secret}")
    private String githubSec;

    @Value("${spring.datasource.username}")
    private String DBname;

    @Value("${spring.datasource.password}")
    private String dbSec;


    @Test
    public void encryptPwd(){
        String OssAK = stringEncryptor.encrypt(OSSAK);
        String OssKey= stringEncryptor.encrypt(OSSKEY);
        String GID = stringEncryptor.encrypt(githubID);
        String GSec = stringEncryptor.encrypt(githubSec);
        String DBroot = stringEncryptor.encrypt(DBname);
        String DBSec = stringEncryptor.encrypt(dbSec);
        System.out.println("OSSAK="+OssAK);
        System.out.println("OSSkey="+OssKey);
        System.out.println("GitID="+GID);
        System.out.println("GitSec="+GSec);
        System.out.println("DBroot="+DBroot);
        System.out.println("bdsec="+DBSec);
    }


}
