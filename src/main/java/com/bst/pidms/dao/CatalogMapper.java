package com.bst.pidms.dao;

import com.bst.pidms.entity.Catalog;
import java.util.List;

public interface CatalogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Catalog record);

    Catalog selectByPrimaryKey(Integer id);

    List<Catalog> selectAll();

    int updateByPrimaryKey(Catalog record);
}