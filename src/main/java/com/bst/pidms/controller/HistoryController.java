package com.bst.pidms.controller;

import com.bst.pidms.entity.History;
import com.bst.pidms.service.HistoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: BST
 * @Date: 2019/4/23 18:58
 */
@RestController
public class HistoryController {
    @Autowired
    HistoryService historyService;

    @GetMapping("gethistory/{id}")
    public PageInfo<History> getAllHistories(@PathVariable("id") Integer pageNum) {
        Integer pageSize = 9;
        PageHelper.startPage(pageNum, pageSize);
        Integer userId = 1;
        List<History> allById = historyService.getAllById(userId);
        PageInfo<History> historyPageInfo = new PageInfo<>(allById);
        return historyPageInfo;
    }

    public void addHistory(Long time, String record, Integer userId) {
        historyService.addHistory(time, record, userId);
    }
}
