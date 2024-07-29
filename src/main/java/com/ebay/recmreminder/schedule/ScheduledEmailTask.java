package com.ebay.recmreminder.schedule;

import com.ebay.recmreminder.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
public class ScheduledEmailTask {

    @Autowired
    private EmailService emailService;

    private final List<String> recipients = Arrays.asList(
            "donghuili@ebay.com",
            "duoyang@ebay.com",
            "menghu@ebay.com",
            "ywu4@ebay.com",
            "zhkang@ebay.com",
            "minxwu@ebay.com",
            "sidwang@ebay.com",
            "cchen22@ebay.com"
    );

    private int currentIndex = 0;

    @Scheduled(cron = "0 0 10 ? * MON,WED", zone = "Asia/Shanghai")
//    @Scheduled(cron = "0 16 14 ? * MON,WED", zone = "Asia/Shanghai")
    public void sendEmail() {
        String to = recipients.get(currentIndex);

        String name = to.split("@")[0];

        String subject = name + "，轮到你咯，去点击recm-all的那个测试";
        String text = """
                今天轮到你咯，去点击recm-all的那个测试
                
                应该是这个链接（ https://pipes.m3.ebay.com/?filter=Merged_MFE_APP_release_test&onepipe=Merged_MFE_APP_release_test ），进不去的话去recm-all里看看
                
                操作文档：https://docs.google.com/document/d/1_eUPOCu9EOGHoyTMg8fJINQws79ZHuCLL9fE6pFynwY/edit
                (文档中的username和password是你的ebay账号和密码，不是你的admin和admin)
                """;

        emailService.sendSimpleEmail(to, subject, text);

        currentIndex = (currentIndex + 1) % recipients.size();
        System.out.println("Email sent to: " + to + " at " + LocalDateTime.now(ZoneId.of("Asia/Shanghai"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    // 东辉请假，先让陈程上
    @EventListener(ApplicationReadyEvent.class)
    public void sendEmailToMe() {
        String to = "cchen22@ebay.com";

        String name = to.split("@")[0];

        String subject = name + "，轮到你咯，去点击recm-all的那个测试";
        String text = """
                今天轮到你咯，去点击recm-all的那个测试
                
                应该是这个链接（ https://pipes.m3.ebay.com/?filter=Merged_MFE_APP_release_test&onepipe=Merged_MFE_APP_release_test ），进不去的话去recm-all里看看
                
                操作文档：https://docs.google.com/document/d/1_eUPOCu9EOGHoyTMg8fJINQws79ZHuCLL9fE6pFynwY/edit
                (文档中的username和password是你的ebay账号和密码，不是你的admin和admin)
                """;
        emailService.sendSimpleEmail(to, subject, text);

        System.out.println("启动成功 " + LocalDateTime.now(ZoneId.of("Asia/Shanghai"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
