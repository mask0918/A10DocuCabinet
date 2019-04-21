package com.bst.pidms.dao;

import com.bst.pidms.entity.BindLabelFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BindLabelFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BindLabelFile record);

    BindLabelFile selectByPrimaryKey(Integer id);

    List<BindLabelFile> selectAll();

    int updateByPrimaryKey(BindLabelFile record);

    int selectCountByCategory(@Param("labelId") Integer labelId, @Param("category") Integer category);

    List<Integer> selectFileIdByLabelId(Integer id);
}