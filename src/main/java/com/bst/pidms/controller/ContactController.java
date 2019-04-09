package com.bst.pidms.controller;

import com.bst.pidms.entity.Contact;
import com.bst.pidms.entity.Permission;
import com.bst.pidms.entity.Role;
import com.bst.pidms.service.ContactService;
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

    @RequestMapping(value = "/contactmanage")
    public String getUser(Map<String, Object> map) {
        List<Contact> contacts = contactService.getContacts();
        map.put("contacts", contacts);
        return "contact";
    }

    @RequestMapping(value = "/delcontact", method = RequestMethod.DELETE)
    public String delContact(@RequestParam("id") String id) {
        contactService.delContact(Integer.parseInt(id));
        return "success";
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
