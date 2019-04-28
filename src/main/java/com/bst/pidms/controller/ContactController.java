package com.bst.pidms.controller;

import com.bst.pidms.entity.Contact;
import com.bst.pidms.entity.Permission;
import com.bst.pidms.entity.Role;
import com.bst.pidms.enums.opEnum;
import com.bst.pidms.service.ContactService;
import com.bst.pidms.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: BST
 * @Date: 2019/4/7 20:49
 */
@Controller
public class ContactController {
    @Autowired
    private ContactService contactService;

    @Autowired
    HistoryService historyService;

    @RequestMapping(value = "/contactmanage")
    public String getUser(Map<String, Object> map) {
        List<Contact> contacts = contactService.getContacts();
        map.put("contacts", contacts);
        return "contact";
    }


    @RequestMapping(value = "/delcontact", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delContact(@RequestParam("id") Integer id) {
        Integer userId = 1;
        Map<String, Object> map = new HashMap<String, Object>();
        contactService.delContact(id);
        map.put("success", true);
        StringBuffer sb = new StringBuffer();
        sb.append(opEnum.DELETE_CONTACT.getName());
        sb.append("\"" + contactService.getContactById(id).getRemark() + "\" ");
        sb.append("\"" + contactService.getContactById(id).getEmail() + "\"");
        historyService.addHistory(System.currentTimeMillis(), sb.toString(), userId);
        return map;
    }

    @RequestMapping(value = "/addcontact", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addContact(@RequestParam("name") String name, @RequestParam("email") String email) {
        Integer userId = 1;
        Map<String, Object> map = new HashMap<String, Object>();
        Contact contact = new Contact();
        contact.setRemark(name);
        contact.setEmail(email);
        contact.setUserId(userId);
        contactService.addContact(contact);
        map.put("success", true);
        StringBuffer sb = new StringBuffer();
        sb.append(opEnum.ADD_CONTACT.getName());
        sb.append("\"" + name + "\" ");
        sb.append("\"" + email + "\"");
        historyService.addHistory(System.currentTimeMillis(), sb.toString(), userId);
        return map;
    }

    @RequestMapping(value = "/modifycontact", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyPermission(@RequestParam("id") Integer id, @RequestParam("name") String name, @RequestParam("mail") String mail) {
        Map<String, Object> map = new HashMap<String, Object>();
        Contact contact = contactService.getContactById(id);
        contact.setId(id);
        contact.setRemark(name);
        contact.setEmail(mail);
        contactService.modifyContact(contact);
        map.put("success", true);
        return map;
    }

}
