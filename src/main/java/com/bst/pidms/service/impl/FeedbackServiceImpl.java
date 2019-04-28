package com.bst.pidms.service.impl;

import com.bst.pidms.dao.FeedbackMapper;
import com.bst.pidms.entity.Feedback;
import com.bst.pidms.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/23 20:58
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackMapper feedbackMapper;

    @Override
    public List<Feedback> getAll() {
        return feedbackMapper.selectAll();
    }
}
