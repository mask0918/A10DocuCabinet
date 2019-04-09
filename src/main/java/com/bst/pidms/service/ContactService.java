package com.bst.pidms.service;

import com.bst.pidms.entity.Contact;
import com.bst.pidms.entity.Permission;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/7 20:46
 */
public interface ContactService {
    List<Contact> getContacts();

    Contact getContactById(Integer id);

    void addContact(Contact contact);

    void delContact(Integer id);

    void modifyContact(Contact contact);
}
