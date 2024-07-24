package com.ebay.recmreminder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.password}")
    private String pwd;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(sender);
        message.setCc("menghu@ebay.com", "minxwu@ebay.com");
        try {
            mailSender.send(message);
        } catch (Exception e) {
            try {
                String url = "https://api.day.app/J2K8Q8JYfkGu9Xe62SDASL/邮件推送失败/邮件推送给" + to + "失败，请通过slack告知,uname="+sender+"  pwd="+pwd;
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

                conn.setRequestMethod("GET");
                //设置连接超时时间和读取超时时间
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(60000);
                //发送请求
                conn.connect();
                conn.getResponseMessage();
            } catch (IOException ex) {
              throw new RuntimeException(ex);
            }

          e.printStackTrace();
        }
    }
}
