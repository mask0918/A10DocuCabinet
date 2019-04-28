package com.bst.pidms.service;

import com.bst.pidms.entity.Mailbox;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/22 18:31
 */
public interface MailboxService {
    public List<Mailbox> getAll(Integer userId);

    public void sendMail(JavaMailSenderImpl mailSender, Mailbox mailbox, Integer[] ids);
}
