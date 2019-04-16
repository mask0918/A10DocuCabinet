package com.bst.pidms.dao;

import com.bst.pidms.entity.Contact;
import com.bst.pidms.entity.OwnFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OwnFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OwnFile record);

    int insertSelective(OwnFile record);

    OwnFile selectByPrimaryKey(Integer id);

    List<OwnFile> selectAll();

    int updateByPrimaryKeySelective(OwnFile record);

    int updateByPrimaryKey(OwnFile record);

    List<OwnFile> selectCategory(Integer category);

    int setCollectStatus(@Param("id") Integer id, @Param("collection") Integer collection);

    int setAttentionStatus(@Param("id") Integer id, @Param("attention") Integer attention);
}