package com.bst.pidms.esmapper;

import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.entity.Permission;
import com.bst.pidms.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/3 14:50
 */
public interface EsFileMapper extends ElasticsearchRepository<OwnFile, Integer> {
    List<OwnFile> findByNameOrInfo(String name, String info);
}
