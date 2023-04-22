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

    @Value("${spring.mail.password}")
    private String mailSec;


    @Test
    public void encryptPwd(){
        //生成密钥
        String gitHubSec = stringEncryptor.decrypt("");
        System.out.println(gitHubSec);
    }


}
