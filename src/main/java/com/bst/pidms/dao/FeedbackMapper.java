package com.bst.pidms.dao;

import com.bst.pidms.entity.Feedback;

import java.util.List;

public interface FeedbackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Feedback record);

    Feedback selectByPrimaryKey(Integer id);

    List<Feedback> selectAll();

    int updateByPrimaryKey(Feedback record);
}