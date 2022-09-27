package com.friday.peanutbutter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class JasyptProfile {
    public static String secret;

    public static String getSecret() {
        return JasyptProfile.secret;
    }
    @Value("${jasypt.encryptor.password}")
    public void setSecret(String secret) {
        this.secret = secret;
    }
}
