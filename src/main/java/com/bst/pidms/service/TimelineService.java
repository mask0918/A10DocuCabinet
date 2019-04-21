package com.bst.pidms.service;

import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.entity.Timeline;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/21 15:35
 */
public interface TimelineService {
    Integer addTimelineIfNotExist(String name);

    List<Timeline> getAll();

    void addBindTimelineFile(Integer fId, Integer tId);

    List<OwnFile> getFilesById(Integer id);
}
