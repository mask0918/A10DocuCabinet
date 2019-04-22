package com.bst.pidms.service.impl;

import com.bst.pidms.dao.MailboxMapper;
import com.bst.pidms.entity.Mailbox;
import com.bst.pidms.service.MailboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/22 18:32
 */
@Service
public class MailboxServiceImpl implements MailboxService {
    @Autowired
    MailboxMapper mailboxMapper;

    @Override
    public List<Mailbox> getAll(Integer userId) {
        return mailboxMapper.selectByUser(userId);
    }

    @Override
    public void sendMail(Mailbox mailbox) {
        mailboxMapper.insert(mailbox);
    }
}
