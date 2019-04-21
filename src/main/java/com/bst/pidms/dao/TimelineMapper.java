package com.bst.pidms.dao;

import com.bst.pidms.entity.Timeline;

import java.util.List;

public interface TimelineMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Timeline record);

    Timeline selectByPrimaryKey(Integer id);

    Timeline selectByName(String name);

    List<Timeline> selectAll();

    int updateByPrimaryKey(Timeline record);
}