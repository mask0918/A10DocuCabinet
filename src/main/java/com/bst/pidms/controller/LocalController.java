package com.bst.pidms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/5/19 17:22
 * Just for test
 */
@RestController
public class LocalController {

    static public List<String> result;

    @RequestMapping("/local")
    public List<String> getResult() {
        result = new ArrayList<>();
        File file = new File("F:/");
        // 调用lists方法
        func(file);
        return result;
    }

    private static void func(File file) {
        File[] fs = file.listFiles();
        if (fs != null) {
            for (File f : fs) {
                if (f.isDirectory())    //若是目录，则递归打印该目录下的文件
                    func(f);
                if (f.isFile())        //若是文件，直接打印
                    result.add(f.getAbsolutePath());
            }
        }
    }

}
