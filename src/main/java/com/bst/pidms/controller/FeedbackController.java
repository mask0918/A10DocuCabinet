package com.bst.pidms.controller;

import com.bst.pidms.entity.Feedback;
import com.bst.pidms.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: BST
 * @Date: 2019/4/23 20:59
 */
@Controller
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @RequestMapping("feedback")
    public String getFeedbacks(Map<String, Object> map) {
        List<Feedback> all = feedbackService.getAll();
        map.put("feedbacks", all);
        return "feedback";
    }

}
