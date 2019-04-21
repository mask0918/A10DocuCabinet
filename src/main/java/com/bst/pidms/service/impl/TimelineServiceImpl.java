package com.bst.pidms.service.impl;

import com.bst.pidms.dao.BindFileTimelineMapper;
import com.bst.pidms.dao.OwnFileMapper;
import com.bst.pidms.dao.TimelineMapper;
import com.bst.pidms.entity.BindFileTimeline;
import com.bst.pidms.entity.Comment;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.entity.Timeline;
import com.bst.pidms.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/21 15:35
 */
@Service
public class TimelineServiceImpl implements TimelineService {

    @Autowired
    OwnFileMapper ownFileMapper;

    @Autowired
    TimelineMapper timelineMapper;

    @Autowired
    BindFileTimelineMapper bindFileTimelineMapper;

    @Override
    public Integer addTimelineIfNotExist(String name) {
        Timeline select = timelineMapper.selectByName(name);
        if (select != null) return select.getId();
        Timeline timeline = new Timeline();
        timeline.setDate(name);
        timelineMapper.insert(timeline);
        return timeline.getId();
    }

    @Override
    public List<Timeline> getAll() {
        return timelineMapper.selectAll();
    }

    @Override
    public void addBindTimelineFile(Integer fId, Integer tId) {
        BindFileTimeline bindFileTimeline = new BindFileTimeline();
        bindFileTimeline.setFileId(fId);
        bindFileTimeline.setTimelineId(tId);
        bindFileTimelineMapper.insert(bindFileTimeline);
    }

    @Override
    public List<OwnFile> getFilesById(Integer id) {
        List<Integer> integers = bindFileTimelineMapper.selectByTime(id);
        List<OwnFile> list = ownFileMapper.selectForeachByIds(integers);
        return list;
    }


}
