package com.bst.pidms.service;

import com.bst.pidms.entity.OwnFile;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/5 11:45
 */
public interface OwnFileService {
    public OwnFile getFileById(Integer id);

    public void addFile(OwnFile file);

    public List<OwnFile> getCategory(Integer category);

    public List<OwnFile> getByCatalog(Integer id);

    public List<OwnFile> getAll();

    public void setCollect(Integer id, Integer flag);

    public void setAttention(Integer id, Integer flag);

}
