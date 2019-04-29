package com.bst.pidms.service;

import com.bst.pidms.entity.Catalog;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/16 23:33
 */
public interface CatalogService {
    List<Catalog> getContactsByParentId(Integer pid);

    Catalog getContactById(Integer id);

    Integer addCatalog(Integer userId, Integer pId, String name);

    void delCatalog(Integer id);

    List<Catalog> getAll();

    Integer getRootId(Integer userId);

}
