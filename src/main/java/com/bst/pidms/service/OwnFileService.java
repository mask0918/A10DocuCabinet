package com.bst.pidms.service;

import com.bst.pidms.entity.OwnFile;
import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/5 11:45
 */
public interface OwnFileService {
    public void updateFile(OwnFile ownFile);

    public OwnFile getFileById(Integer id);

    public void addFile(OwnFile file);

    public void deleteFileById(Integer id);

    public List<OwnFile> getCategory(Integer category);

    public List<OwnFile> getByCatalog(Integer id);

    public List<OwnFile> getAll();

    public List<OwnFile> getFilingInfo(Integer labelId);

    public void setCollect(Integer id, Integer flag);

    public void setAttention(Integer id, Integer flag);

    public void getImageResults(OwnFile ownFile, File file);

    public void getAudioResults(OwnFile ownFile, File file);

    public void getVideoResults(OwnFile ownFile, File file);

    public void getDocumentResults(OwnFile ownFile, File file);

    public void getOtherResults(OwnFile ownFile, File file);


}
