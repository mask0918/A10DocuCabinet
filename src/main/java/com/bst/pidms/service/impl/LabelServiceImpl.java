package com.bst.pidms.service.impl;

import com.bst.pidms.dao.BindLabelFileMapper;
import com.bst.pidms.dao.LabelMapper;
import com.bst.pidms.entity.Label;
import com.bst.pidms.entity.OwnFile;
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

    @Override
    public Integer addLabelIfNotExist(Integer userId, String name, Boolean insight) {
        Label temp = new Label();
        temp.setUserId(userId);
        temp.setName(name);
        temp.setInsight(insight);
        List<Label> labels = labelMapper.selectByCondition(temp);
        if (labels.size() > 0) return labels.get(0).getId();
        Label label = new Label();
        label.setUserId(userId);
        label.setName(name);
        label.setInsight(insight);
        labelMapper.insert(label);
        return label.getId();
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
}
