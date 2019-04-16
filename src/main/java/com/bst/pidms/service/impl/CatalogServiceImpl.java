package com.bst.pidms.service.impl;

import com.bst.pidms.dao.CatalogMapper;
import com.bst.pidms.entity.Catalog;
import com.bst.pidms.service.CatalogService;
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

    @Override
    public List<Catalog> getContactsByParentId(Integer pid) {
        return catalogMapper.selectByParentId(pid);
    }

    @Override
    public Catalog getContactById(Integer id) {
        return catalogMapper.selectByPrimaryKey(id);
    }
}
