package com.bst.pidms.controller;

import com.bst.pidms.entity.Catalog;
import com.bst.pidms.esmapper.EsCatalogMapper;
import com.bst.pidms.esmapper.EsFileMapper;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.enums.FileType;
import com.google.common.collect.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: BST
 * @Date: 2019/4/19 19:12
 */
@RestController
public class ElasticsearchController {
    @Autowired
    EsFileMapper esFileMapper;

    @Autowired
    EsCatalogMapper esCatalogMapper;

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public Map<String, Object> search(@RequestParam("way") Integer id, @RequestParam("text") String content) {
        Map<String, Object> map = new HashMap<>();
        List<OwnFile> list = null;
        switch (id) {
            case 1:
                List<Catalog> catalogs = pathSearch(content);
                map.put("CATALOG", catalogs);
                break;
            case 2:
            case 3:
                list = keytagSearch(content);
                break;
            case 4:
            case 5:
                list = nameSearch(content);
                break;
            case 6:
        }
        if (list != null) {
            Map<Integer, List<OwnFile>> collect = list.stream().collect(Collectors.groupingBy(OwnFile::getCategory));
            collect.forEach((k, v) -> map.put(FileType.fileEnum(k).name(), v));
        }
        return map;
    }

    public List<OwnFile> keytagSearch(String content) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.multiMatchQuery(content, "tag", "keyword"));
        queryBuilder.withQuery(boolQueryBuilder);
        Iterable<OwnFile> search = esFileMapper.search(queryBuilder.build());
        List<OwnFile> ownFiles = Lists.newArrayList(search);
        return ownFiles;
    }

    public List<OwnFile> nameSearch(String content) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.multiMatchQuery(content, "name"));
        queryBuilder.withQuery(boolQueryBuilder);
        Iterable<OwnFile> search = esFileMapper.search(queryBuilder.build());
        List<OwnFile> ownFiles = Lists.newArrayList(search);
        return ownFiles;
    }

    public List<Catalog> pathSearch(String content) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.multiMatchQuery(content, "name"));
        queryBuilder.withQuery(boolQueryBuilder);
        Iterable<Catalog> search = esCatalogMapper.search(queryBuilder.build());
        List<Catalog> catalogs = Lists.newArrayList(search);
        return catalogs;
    }
}
