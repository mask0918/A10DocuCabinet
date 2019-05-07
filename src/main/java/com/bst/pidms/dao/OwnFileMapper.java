package com.bst.pidms.dao;

import com.bst.pidms.entity.Contact;
import com.bst.pidms.entity.OwnFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.*;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Map;

public interface OwnFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OwnFile record);

    int insertSelective(OwnFile record);

    OwnFile selectByPrimaryKey(Integer id);

    List<OwnFile> selectIfCollect(Integer userId);

    List<OwnFile> selectIfAttention(Integer userId);

    List<OwnFile> selectAll();

    int updateByPrimaryKeySelective(OwnFile record);

    int updateByPrimaryKey(OwnFile record);

    List<OwnFile> selectForeachByIds(List<Integer> ids);

    List<OwnFile> selectCategory(@Param("id") Integer category, @Param("userId") Integer userId);

    List<OwnFile> selectByCatalogId(Integer id);

    List<String> selectTimelineByUserId(Integer userId);

    List<OwnFile> selectFileByTimeline(@Param("userId") Integer userId, @Param("timeline") String date);

    int setCollectStatus(@Param("id") Integer id, @Param("collection") Integer collection);

    int setAttentionStatus(@Param("id") Integer id, @Param("attention") Integer attention);

}