package com.bst.pidms.controller;

import com.bst.pidms.entity.Contact;
import com.bst.pidms.entity.Mailbox;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.entity.User;
import com.bst.pidms.enums.opEnum;
import com.bst.pidms.service.*;
import com.bst.pidms.utils.SessionUtil;
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
    UserService userService;

    @Autowired
    ContactService contactService;

    @Autowired
    MailboxService mailboxService;

    @Autowired
    OwnFileService ownFileService;

    @Autowired
    HistoryService historyService;

    @GetMapping("mailinfo")
    public Map<String, Object> mailPageInfo() {
        Map<String, Object> map = new HashMap<>();
        Integer userId = SessionUtil.getInstance().getIdNumber();
        if (userId == -1) return null;
        User user = userService.getUserById(userId);
        map.put("acc", user.getMailAcc());
        map.put("pwd", user.getMailPwd());
        map.put("smtp", user.getSmtp());
        List<Contact> contacts = contactService.getContactsByUserId(userId);
        map.put("contacts", contacts);
        List<Mailbox> mails = mailboxService.getAll(userId);
        map.put("mails", mails);
        return map;
    }

    @PostMapping("sendmail")
    public Map<String, Object> sendMail(@RequestParam("rec") String rec, @RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("ids") Integer[] ids) {
        Map<String, Object> map = new HashMap<>();
        Integer userId = SessionUtil.getInstance().getIdNumber();
        if (userId == -1) {
            map.put("success", false);
            return map;
        }
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // 设置参数
        mailSender.setHost("smtp.qq.com");
        mailSender.setUsername("bst-w@qq.com");
        mailSender.setPassword("qynlaunfuagncacb");
        Mailbox mailbox = new Mailbox();
        mailbox.setContent(content);
        mailbox.setUserId(userId);
        mailbox.setRecipient(rec);
        mailbox.setTitle(title);
        mailbox.setTime(System.currentTimeMillis());
        mailbox.setSender("bst-w@qq.com");
        mailbox.setFileIndex(ArrayUtils.toString(ids));
        mailboxService.sendMail(mailSender, mailbox, ids);
        map.put("success", true);
        StringBuffer sb = new StringBuffer();
        sb.append(opEnum.SEND_MAIL.getName());
        sb.append("发送邮箱至 ");
        sb.append("\"" + rec + "\"");
        historyService.addHistory(System.currentTimeMillis(), sb.toString(), userId);
        return map;
    }

}
