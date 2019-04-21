package com.bst.pidms.esmapper;

import com.bst.pidms.entity.Catalog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author: BST
 * @Date: 2019/4/21 13:38
 */
public interface EsCatalogMapper extends ElasticsearchRepository<Catalog, Integer> {
}
