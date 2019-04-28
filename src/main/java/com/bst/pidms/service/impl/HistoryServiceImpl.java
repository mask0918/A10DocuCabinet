package com.bst.pidms.service.impl;

import com.bst.pidms.dao.HistoryMapper;
import com.bst.pidms.entity.History;
import com.bst.pidms.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/23 18:56
 */
@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    HistoryMapper historyMapper;

    @Override
    public List<History> getAllById(Integer userId) {
        return historyMapper.selectByUser(userId);
    }

    @Override
    public void addHistory(Long time, String record, Integer userId) {
        History history = new History();
        history.setRecord(record);
        history.setTime(time);
        history.setUserId(userId);
        historyMapper.insert(history);
    }

    @Override
    public List<History> getAll() {
        return historyMapper.selectAll();
    }
}
