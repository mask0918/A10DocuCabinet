package com.bst.pidms.service.impl;

import com.bst.pidms.dao.CatalogMapper;
import com.bst.pidms.entity.Catalog;
import com.bst.pidms.esmapper.EsCatalogMapper;
import com.bst.pidms.service.CatalogService;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/16 23:34
 */
@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    CatalogMapper catalogMapper;

    @Autowired
    EsCatalogMapper esCatalogMapper;

    @Override
    public List<Catalog> getContactsByParentId(Integer pid) {
        return catalogMapper.selectByParentId(pid);
    }

    @Override
    public Catalog getContactById(Integer id) {
        return catalogMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer addCatalog(Integer userId, Integer pId, String name) {
        Catalog catalog = new Catalog();
        catalog.setUserId(userId);
        catalog.setPid(pId);
        catalog.setName(name);
        catalogMapper.insert(catalog);
        esCatalogMapper.save(catalog);
        return catalog.getId();
    }

    @Override
    public void delCatalog(Integer id) {
        catalogMapper.deleteByPrimaryKey(id);
        esCatalogMapper.deleteById(id);
    }

    @Override
    public List<Catalog> getAll() {
        return catalogMapper.selectAll();
    }

    @Override
    public Integer getRootId(Integer userId) {
        return catalogMapper.selectRootByUserId(userId);
    }
}
