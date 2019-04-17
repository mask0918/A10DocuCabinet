package com.bst.pidms.service;

import com.bst.pidms.entity.Label;
import com.bst.pidms.entity.OwnFile;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/17 15:56
 */
public interface LabelService {
    public Integer addLabelIfNotExist(Integer userId, String name, Boolean insight);

    public List<Label> getLabel(Integer userId, String name, Boolean insight);

    public List<Label> getLabelByCategory(Integer userId, Integer Category, Boolean insight);

}
