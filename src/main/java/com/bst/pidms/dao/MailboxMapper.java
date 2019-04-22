package com.bst.pidms.dao;

import com.bst.pidms.entity.Mailbox;

import java.util.List;

public interface MailboxMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Mailbox record);

    Mailbox selectByPrimaryKey(Integer id);

    List<Mailbox> selectAll();

    List<Mailbox> selectByUser(Integer userId);

    int updateByPrimaryKey(Mailbox record);
}