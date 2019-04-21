package com.bst.pidms.esmapper;

import com.bst.pidms.entity.Catalog;
import com.bst.pidms.entity.Comment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author: BST
 * @Date: 2019/4/21 16:33
 */
public interface EsCommentMapper extends ElasticsearchRepository<Comment, Integer> {
}
