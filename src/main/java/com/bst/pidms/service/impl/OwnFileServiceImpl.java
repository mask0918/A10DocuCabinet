package com.bst.pidms.service.impl;

import com.bst.pidms.dao.OwnFileMapper;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.service.OwnFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: BST
 * @Date: 2019/4/5 11:45
 */
@Service
public class OwnFileServiceImpl implements OwnFileService {
    @Autowired
    private OwnFileMapper ownFileMapper;

    @Override
    public OwnFile getFileById(Integer id) {
        return ownFileMapper.selectByPrimaryKey(id);
    }
}
