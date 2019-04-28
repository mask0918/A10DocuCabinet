package com.bst.pidms.esmapper;

import com.bst.pidms.entity.Label;
import com.bst.pidms.entity.OwnFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/23 15:23
 */
public interface EsLabelMapper extends ElasticsearchRepository<Label, Integer> {
}
