package com.bst.pidms.controller;

import com.bst.pidms.Resource;
import com.bst.pidms.entity.User;
import com.bst.pidms.service.UserService;
import com.google.common.collect.Lists;
import org.apache.http.HttpRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String aaa(@RequestParam("uploadfile") MultipartFile file, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("loginUser");

        if (user == null || file.getSize() == 0) return "error";
        if (file.getSize() == 0) return "error";
        user = "zzz";

        File dir = new File("D:\\InsightPIDMS\\" + user.toString());
        if (!dir.exists()) dir.mkdir();

        File toFile = new File(dir.getAbsolutePath() + "\\" + file.getOriginalFilename().toLowerCase());
        toFile.createNewFile();
        file.transferTo(toFile);
        return "success";
    }
}
