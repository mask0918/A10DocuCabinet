package com.bst.pidms.service.impl;

import com.bst.pidms.dao.BindLabelFileMapper;
import com.bst.pidms.entity.BindLabelFile;
import com.bst.pidms.service.BindLabelFileService;
import com.bst.pidms.service.BindRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: BST
 * @Date: 2019/4/17 16:21
 */
@Service
public class BindLabelFileServiceImpl implements BindLabelFileService {
    @Autowired
    BindLabelFileMapper bindLabelFileMapper;

    @Override
    public void addBind(Integer lId, Integer fId, Integer category) {
        BindLabelFile bindLabelFile = new BindLabelFile();
        bindLabelFile.setCategory(category);
        bindLabelFile.setLabelId(lId);
        bindLabelFile.setFileId(fId);
        bindLabelFileMapper.insert(bindLabelFile);
    }
}
