package com.bst.pidms.dao;

import com.bst.pidms.entity.OwnFile;
import java.util.List;

public interface OwnFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OwnFile record);

    OwnFile selectByPrimaryKey(Integer id);

    List<OwnFile> selectAll();

    int updateByPrimaryKey(OwnFile record);
}