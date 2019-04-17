package com.bst.pidms.service.impl;

import com.bst.pidms.dao.OwnFileMapper;
import com.bst.pidms.entity.FileType;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.service.OwnFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void addFile(OwnFile file) {
        ownFileMapper.insertSelective(file);
    }

    @Override
    public List<OwnFile> getCategory(Integer id) {
        return ownFileMapper.selectCategory(id);
    }

    @Override
    public List<OwnFile> getByCatalog(Integer id) {
        return ownFileMapper.selectByCatalogId(id);
    }

    @Override
    public List<OwnFile> getAll() {
        return ownFileMapper.selectAll();
    }

    @Override
    public void setCollect(Integer id, Integer flag) {
        ownFileMapper.setCollectStatus(id, flag);
    }

    @Override
    public void setAttention(Integer id, Integer flag) {
        ownFileMapper.setAttentionStatus(id, flag);
    }
}
