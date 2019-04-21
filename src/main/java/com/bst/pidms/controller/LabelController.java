package com.bst.pidms.controller;

import com.bst.pidms.enums.FileType;
import com.bst.pidms.entity.Label;
import com.bst.pidms.service.BindLabelFileService;
import com.bst.pidms.service.LabelService;
import com.bst.pidms.service.OwnFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: BST
 * @Date: 2019/4/17 19:43
 */
@RestController
public class LabelController {
    @Autowired
    LabelService labelService;

    @Autowired
    BindLabelFileService bindLabelFileService;

    @Autowired
    OwnFileService ownFileService;

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

    /**
     * 获取文档智能归档 虚拟目录
     *
     * @return
     */
    @RequestMapping("getdocuinsight")
    public List<Label> getDocuFiling() {
        Integer userId = 1;
        List<Label> labels = labelService.getLabelByCategory(userId, FileType.DOCUMENT.getValue(), true);
        return labels;
    }

    /**
     * 获取图片智能归档 虚拟目录
     *
     * @return
     */
    @RequestMapping("getimageinsight")
    public List<Label> getImageFiling() {
        Integer userId = 1;
        List<Label> labels = labelService.getLabelByCategory(userId, FileType.IMAGE.getValue(), true);
        return labels;
    }

    /**
     * 获取视频智能归档 虚拟目录
     *
     * @return
     */
    @RequestMapping("getvideoinsight")
    public List<Label> getVideoFiling() {
        Integer userId = 1;
        List<Label> labels = labelService.getLabelByCategory(userId, FileType.VIDEO.getValue(), true);
        return labels;
    }

    @RequestMapping(value = "createdefined", method = RequestMethod.POST)
    public Map<String, Object> createDefined(@RequestParam("name") String name, @RequestParam("ids") Integer[] ids, @RequestParam("category") Integer[] categories) {
        Map<String, Object> map = new HashMap<>();
        Integer userId = 1;
        Integer id = labelService.addLabelIfNotExist(userId, name, false);
        for (int i = 0; i < ids.length; i++) {
            bindLabelFileService.addBind(id, ids[i], categories[i]);
        }
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "movedefined", method = RequestMethod.POST)
    public Map<String, Object> moveDefined(@RequestParam("id") Integer id, @RequestParam("ids") Integer[] ids, @RequestParam("category") Integer[] categories) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < ids.length; i++) {
            bindLabelFileService.addBind(id, ids[i], categories[i]);
        }
        map.put("success", true);
        return map;
    }

}
