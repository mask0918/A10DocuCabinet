package com.bst.pidms.service.impl;

import com.bst.pidms.dao.MailboxMapper;
import com.bst.pidms.dao.OwnFileMapper;
import com.bst.pidms.entity.Mailbox;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.service.MailboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/22 18:32
 */
@Service
public class MailboxServiceImpl implements MailboxService {
    @Autowired
    MailboxMapper mailboxMapper;

    @Autowired
    OwnFileMapper ownFileMapper;

    @Override
    public List<Mailbox> getAll(Integer userId) {
        return mailboxMapper.selectByUser(userId);
    }

    @Override
    @Async
    public void sendMail(JavaMailSenderImpl mailSender, Mailbox mailbox, Integer[] ids) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "GBK");
            messageHelper.setFrom("bst-w@qq.com");
            messageHelper.setTo(mailbox.getSender());
            messageHelper.setSubject(mailbox.getTitle());
            messageHelper.setText(mailbox.getContent(), true);
            for (Integer id : ids) {
                OwnFile fileById = ownFileMapper.selectByPrimaryKey(id);
                if (fileById != null) {
                    messageHelper.addAttachment(fileById.getName(), new File("D:/InsightPIDMS/" + fileById.getUrl()));
                }
            }
        } catch (MessagingException e) {
            e.getMessage();
        }

        mailboxMapper.insert(mailbox);


    }
}
