package com.bst.pidms.dao;

import com.bst.pidms.entity.BindFileTimeline;

import java.util.List;

public interface BindFileTimelineMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BindFileTimeline record);

    BindFileTimeline selectByPrimaryKey(Integer id);

    List<BindFileTimeline> selectAll();

    int updateByPrimaryKey(BindFileTimeline record);

    List<Integer> selectByTime(Integer id);
}