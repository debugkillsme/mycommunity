package com.friday.peanutbutter.service;

import com.friday.peanutbutter.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;//一定要用@Autowired

    @Autowired
    private UserMapper userMapper;//注入UserMapper，交给bena

    //application.properties中已配置的值
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 给前端输入的邮箱，发送验证码
     * @param email
     * @param session
     * @return
     */
    public boolean sendMimeMail( String email, HttpSession session) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("验证码邮件");//主题
            //生成随机数
            String checkCode = randomCode();
            //将生成的放置到session中
            session.setAttribute("email",email);
            session.setAttribute("checkCode",checkCode);

            mailMessage.setText("您收到的验证码是："+checkCode);//内容
            mailMessage.setTo(email);//发给谁

            mailMessage.setFrom(from);//你自己的邮箱
            mailSender.send(mailMessage);//发送
            return  true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 随机生成6位数的验证码
     * @return String code
     */
    public String randomCode(){
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}

