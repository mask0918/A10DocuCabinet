package com.bst.pidms.service.impl;

import com.bst.pidms.dao.BindLabelFileMapper;
import com.bst.pidms.dao.LabelMapper;
import com.bst.pidms.entity.Label;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.esmapper.EsLabelMapper;
import com.bst.pidms.service.LabelService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/17 15:56
 */
@Service
public class LabelServiceImpl implements LabelService {
    @Autowired
    LabelMapper labelMapper;

    @Autowired
    BindLabelFileMapper bindLabelFileMapper;

    @Autowired
    EsLabelMapper esLabelMapper;

    @Override
    public Integer addLabelIfNotExist(Integer userId, String name, Boolean insight) {
        Label temp = new Label();
        temp.setUserId(userId);
        temp.setName(name);
        temp.setInsight(insight);
        temp.setPid(0);
        List<Label> labels = labelMapper.selectByCondition(temp);
        if (labels.size() > 0) return labels.get(0).getId();
        labelMapper.insert(temp);
        esLabelMapper.save(temp);
        return temp.getId();
    }

    @Override
    public List<Label> getLabel(Integer userId, String name, Boolean insight) {
        Label temp = new Label();
        temp.setUserId(userId);
        temp.setName(name);
        temp.setInsight(insight);
        List<Label> labels = labelMapper.selectByCondition(temp);
        return labels;
    }

    @Override
    public List<Label> getLabelByCategory(Integer userId, Integer category, Boolean insight) {
        List<Label> labelList = new ArrayList<Label>();
        Label temp = new Label();
        temp.setUserId(userId);
        temp.setInsight(insight);
        List<Label> labels = labelMapper.selectByCondition(temp);
        for (Label label : labels) {
            int i = bindLabelFileMapper.selectCountByCategory(label.getId(), category);
            if (i > 0) labelList.add(label);
        }
        return labelList;
    }

    @Override
    public List<Label> getLabelByCid(Integer userId, Integer cid) {
        return labelMapper.selectTreeByCid(userId, cid);
    }

    @Override
    public List<Label> getLabelByPid(Integer userId, Integer pid) {
        return labelMapper.selectTreeByPid(userId, pid);
    }
}
