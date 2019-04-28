package com.bst.pidms.service;

import com.bst.pidms.entity.History;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/23 18:54
 */
public interface HistoryService {
    public List<History> getAllById(Integer userId);

    public void addHistory(Long time, String record, Integer userId);

    public List<History> getAll();
}
