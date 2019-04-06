package com.bst.pidms;

import com.bst.pidms.entity.Permission;
import com.bst.pidms.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author: BST
 * @Date: 2019/4/3 14:50
 */
public interface Resource extends ElasticsearchRepository<User, Integer> {
}
