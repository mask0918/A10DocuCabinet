package com.bst.pidms.controller;

import com.bst.pidms.entity.FileType;
import com.bst.pidms.entity.Label;
import com.bst.pidms.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/17 19:43
 */
@RestController
public class LabelController {
    @Autowired
    LabelService labelService;

    /**
     * 获取智能归档 虚拟目录
     *
     * @return
     */
    @RequestMapping("getinsight")
    public List<Label> getIntellgenceFiling() {
        Integer userId = 1;
        List<Label> labelInsight = labelService.getLabel(userId, null, true);
        return labelInsight;
    }

    /**
     * 获取用户自定义归档 虚拟目录
     *
     * @return
     */
    @RequestMapping("getdefined")
    public List<Label> getWithoutIntellgenceFiling() {
        Integer userId = 1;
        List<Label> labelInsight = labelService.getLabel(userId, null, false);
        return labelInsight;
    }

    @RequestMapping("getinsight2docu")
    public List<Label> getDocuFiling() {
        Integer userId = 1;
        List<Label> labels = labelService.getLabelByCategory(userId, FileType.DOCUMENT.getValue(), true);
        return labels;
    }

    @RequestMapping("getdefined2docu")
    public List<Label> getDocuWithoutFiling() {
        Integer userId = 1;
        List<Label> labels = labelService.getLabelByCategory(userId, FileType.DOCUMENT.getValue(), false);
        return labels;
    }


}
