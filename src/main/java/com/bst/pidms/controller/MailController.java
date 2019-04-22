package com.bst.pidms.controller;

import com.bst.pidms.entity.Mailbox;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.service.MailboxService;
import com.bst.pidms.service.OwnFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: BST
 * @Date: 2019/4/22 11:06
 */
@RestController
@Slf4j
public class MailController {
    @Autowired
    MailboxService mailboxService;

    @Autowired
    OwnFileService ownFileService;

    @PostMapping("sendmail")
    public Map<String, Object> sendMail(@RequestParam("rec") String rec, @RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("ids") Integer[] ids) {
        Integer userId = 1;
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // 设置参数
        mailSender.setHost("smtp.qq.com");
        mailSender.setUsername("bst-w@qq.com");
        mailSender.setPassword("qynlaunfuagncacb");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        Map<String, Object> map = new HashMap<>();
        try {
            log.info("Content , {}", content);
            log.info("ids , {}", ArrayUtils.toString(ids));
            Mailbox mailbox = new Mailbox();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "GBK");
            messageHelper.setFrom("bst-w@qq.com");
            messageHelper.setTo(rec);
            messageHelper.setSubject(title);
            messageHelper.setText(content, true);
            for (Integer id : ids) {
                OwnFile fileById = ownFileService.getFileById(id);
                if (fileById != null) {
                    messageHelper.addAttachment(fileById.getName(), new File("D:/InsightPIDMS/" + fileById.getUrl()));
                }
            }
            mailSender.send(mimeMessage);
            mailbox.setContent(content);
            mailbox.setUserId(userId);
            mailbox.setRecipient(rec);
            mailbox.setTitle(title);
            mailbox.setTime(System.currentTimeMillis());
            mailbox.setSender("bst-w@qq.com");
            mailbox.setFileIndex(ArrayUtils.toString(ids));
            mailboxService.sendMail(mailbox);
            map.put("success", true);
            return map;
        } catch (MessagingException e) {
            e.getMessage();
        }
        map.put("success", false);
        return map;
    }

    @GetMapping("getmails")
    public List<Mailbox> getSendMails() {
        Integer userId = 1;
        List<Mailbox> mailboxList = mailboxService.getAll(userId);
        return mailboxList;
    }

}
