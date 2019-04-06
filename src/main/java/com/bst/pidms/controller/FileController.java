package com.bst.pidms.controller;

import com.bst.pidms.Resource;
import com.bst.pidms.entity.User;
import com.bst.pidms.service.UserService;
import com.google.common.collect.Lists;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @Author: BST
 * @Date: 2019/4/3 14:50
 */
@Controller
public class FileController {

    //    上传文件
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String aaa(@RequestParam("uploadfile") MultipartFile file) throws Exception {
        File file1 = new File("D:\\filetest\\" + file.getOriginalFilename().toLowerCase());
        boolean newFile = file1.createNewFile();
        file.transferTo(file1);
        System.out.println(file1.getName());
        System.out.println(file.getSize());
        System.out.println(file.getOriginalFilename());
        return "success";
    }


//    @GetMapping("/query/{id}")
//    public List<User> esTest1(@PathVariable("id") Integer idd) {
////        Optional<User> id = resource.findById(idd);
////        return id.get();
////        Iterable<User> all = resource.findAll();
////        return Lists.newArrayList(all);
//    }

}
