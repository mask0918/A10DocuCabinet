package com.bst.pidms.service.impl;

import com.bst.pidms.dao.ContactMapper;
import com.bst.pidms.entity.Contact;
import com.bst.pidms.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/7 20:48
 */
@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactMapper contactMapper;

    @Override
    public List<Contact> getContacts() {
        return contactMapper.selectAll();
    }

    @Override
    public List<Contact> getContactsByUserId(Integer id) {
        return contactMapper.selectByUser(id);
    }

    @Override
    public Contact getContactById(Integer id) {
        return contactMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addContact(Contact contact) {
        contactMapper.insert(contact);
    }

    @Override
    public void delContact(Integer id) {
        contactMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void modifyContact(Contact contact) {
        contactMapper.updateByPrimaryKey(contact);
    }
}
