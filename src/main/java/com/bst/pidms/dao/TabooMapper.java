package com.bst.pidms.dao;

import com.bst.pidms.entity.Taboo;
import java.util.List;

public interface TabooMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Taboo record);

    Taboo selectByPrimaryKey(Integer id);

    List<Taboo> selectAll();

    int updateByPrimaryKey(Taboo record);
}