package com.bst.pidms.dao;

import com.bst.pidms.entity.History;

import java.util.List;

public interface HistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(History record);

    History selectByPrimaryKey(Integer id);

    List<History> selectAll();

    int updateByPrimaryKey(History record);

    List<History> selectByUser(Integer userId);
}