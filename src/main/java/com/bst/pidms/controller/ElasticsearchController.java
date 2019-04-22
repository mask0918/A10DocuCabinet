package com.bst.pidms.controller;

import com.bst.pidms.entity.Catalog;
import com.bst.pidms.entity.Comment;
import com.bst.pidms.esmapper.EsCatalogMapper;
import com.bst.pidms.esmapper.EsFileMapper;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.enums.FileType;
import com.bst.pidms.service.OwnFileService;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.google.common.collect.Lists;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: BST
 * @Date: 2019/4/19 19:12
 */
@RestController
public class ElasticsearchController {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    EsFileMapper esFileMapper;

    @Autowired
    EsCatalogMapper esCatalogMapper;

    @Autowired
    OwnFileService ownFileService;

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
                list = infoSearch(content);
                break;
            case 3:
                list = keytagSearch(content);
                break;
            case 4:
                list = commentSearch(content);
                break;
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
        SearchQuery queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(content, "keyword", "tag"))
//                .withPageable(PageRequest.of(0, 5))
                .withHighlightFields(new HighlightBuilder.Field("keyword")
                                .preTags("<b><font style='color:#C02A28'>").postTags("</font></b>")
                        , new HighlightBuilder.Field("tag")
                                .preTags("<b><font style='color:#C02A28'>").postTags("</font></b>")
                ).build();

        Page<OwnFile> ownFiles = elasticsearchTemplate.queryForPage(queryBuilder, OwnFile.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                Iterator<SearchHit> iterator = hits.iterator();
                List<OwnFile> list = new ArrayList<>();
                while (iterator.hasNext()) {
                    SearchHit searchHit = iterator.next();
                    String keyword = null;
                    String tag = null;
                    //取高亮结果
                    if (searchHit.getHighlightFields().get("keyword") == null) {
                        System.out.println("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
                        keyword = searchHit.getSourceAsMap().get("keyword").toString();
                    } else {
                        System.out.println("仄仄仄仄仄仄仄仄仄仄仄仄仄仄仄仄仄仄仄仄仄仄仄仄仄 ");
                        keyword = searchHit.getHighlightFields().get("keyword").getFragments()[0].toString();
                    }
                    if (searchHit.getHighlightFields().get("tag") == null) {
                        tag = searchHit.getSourceAsMap().get("tag").toString();
                    } else {
                        tag = searchHit.getHighlightFields().get("tag").getFragments()[0].toString();
                    }

                    //将高亮结果放入实体类中
                    OwnFile entity = new OwnFile();
                    entity.setName(searchHit.getSourceAsMap().get("name").toString());
                    entity.setId(Integer.parseInt(searchHit.getSourceAsMap().get("id").toString()));
                    entity.setUserId(Integer.parseInt(searchHit.getSourceAsMap().get("userId").toString()));
                    entity.setUploadTime(Long.parseLong(searchHit.getSourceAsMap().get("uploadTime").toString()));
                    entity.setServerTime(Long.parseLong(searchHit.getSourceAsMap().get("serverTime").toString()));
                    entity.setSize(Long.parseLong(searchHit.getSourceAsMap().get("size").toString()));
                    entity.setCategory(Integer.parseInt(searchHit.getSourceAsMap().get("category").toString()));
                    entity.setCatalogId(Integer.parseInt(searchHit.getSourceAsMap().get("catalogId").toString()));
                    entity.setUrl(searchHit.getSourceAsMap().get("url").toString());
                    entity.setInfo(searchHit.getSourceAsMap().get("info").toString());
                    entity.setViews(Integer.parseInt(searchHit.getSourceAsMap().get("views").toString()));
                    entity.setScale(Integer.parseInt(searchHit.getSourceAsMap().get("scale").toString()));
                    entity.setAttention(Byte.valueOf(searchHit.getSourceAsMap().get("attention").toString()));
                    entity.setCollection(Byte.valueOf(searchHit.getSourceAsMap().get("collection").toString()));
                    entity.setDownloads(Integer.parseInt(searchHit.getSourceAsMap().get("downloads").toString()));
                    entity.setRecycle(Byte.valueOf(searchHit.getSourceAsMap().get("recycle").toString()));

                    entity.setKeyword(keyword);
                    entity.setTag(tag);
                    //将实体类放入集合中
                    list.add(entity);
                }
                AggregatedPage aggregatedPage = new AggregatedPageImpl(list, pageable, hits.getTotalHits());
                return aggregatedPage;
            }
        });
        for (OwnFile ownFile : ownFiles.getContent()) {
            System.out.println(ownFile);
        }
        return ownFiles.getContent();
    }


    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }

    public List<OwnFile> nameSearch(String content) {
        SearchQuery queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery(content).defaultField("name"))
                .withPageable(PageRequest.of(0, 5))
                .withHighlightFields(new HighlightBuilder.Field("name")
                        .preTags("<b><font style='color:#C02A28'>").postTags("</font></b>")
                ).build();

        Page<OwnFile> ownFiles = elasticsearchTemplate.queryForPage(queryBuilder, OwnFile.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                Iterator<SearchHit> iterator = hits.iterator();
                List<OwnFile> list = new ArrayList<>();
                while (iterator.hasNext()) {
                    SearchHit searchHit = iterator.next();
                    //取高亮结果
                    String name = searchHit.getHighlightFields().get("name").getFragments()[0].toString();

                    //将高亮结果放入实体类中
                    OwnFile entity = new OwnFile();
                    entity.setName(name);
                    entity.setId(Integer.parseInt(searchHit.getSourceAsMap().get("id").toString()));
                    entity.setTag(searchHit.getSourceAsMap().get("tag").toString());
                    entity.setKeyword(searchHit.getSourceAsMap().get("keyword").toString());
                    entity.setUserId(Integer.parseInt(searchHit.getSourceAsMap().get("userId").toString()));
                    entity.setUploadTime(Long.parseLong(searchHit.getSourceAsMap().get("uploadTime").toString()));
                    entity.setServerTime(Long.parseLong(searchHit.getSourceAsMap().get("serverTime").toString()));
                    entity.setSize(Long.parseLong(searchHit.getSourceAsMap().get("size").toString()));
                    entity.setCategory(Integer.parseInt(searchHit.getSourceAsMap().get("category").toString()));
                    entity.setCatalogId(Integer.parseInt(searchHit.getSourceAsMap().get("catalogId").toString()));
                    entity.setUrl(searchHit.getSourceAsMap().get("url").toString());
                    entity.setInfo(searchHit.getSourceAsMap().get("info").toString());
                    entity.setViews(Integer.parseInt(searchHit.getSourceAsMap().get("views").toString()));
                    entity.setScale(Integer.parseInt(searchHit.getSourceAsMap().get("scale").toString()));
                    entity.setAttention(Byte.valueOf(searchHit.getSourceAsMap().get("attention").toString()));
                    entity.setCollection(Byte.valueOf(searchHit.getSourceAsMap().get("collection").toString()));
                    entity.setDownloads(Integer.parseInt(searchHit.getSourceAsMap().get("downloads").toString()));
                    entity.setRecycle(Byte.valueOf(searchHit.getSourceAsMap().get("recycle").toString()));

                    //将实体类放入集合中
                    list.add(entity);
                }
                AggregatedPage aggregatedPage = new AggregatedPageImpl(list, pageable, hits.getTotalHits());
                return aggregatedPage;
            }
        });
        for (OwnFile ownFile : ownFiles.getContent()) {
            System.out.println(ownFile);
        }
        return ownFiles.getContent();
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

    public List<OwnFile> infoSearch(String content) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.multiMatchQuery(content, "info"));
        queryBuilder.withQuery(boolQueryBuilder);
        Iterable<OwnFile> search = esFileMapper.search(queryBuilder.build());
        List<OwnFile> ownFiles = Lists.newArrayList(search);
        return ownFiles;
    }

    public List<OwnFile> commentSearch(String content) {
        SearchQuery queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery(content).defaultField("content"))
                .withPageable(PageRequest.of(0, 5))
                .withHighlightFields(new HighlightBuilder.Field("content")
                        .preTags("<b><font style='color:#C02A28'>").postTags("</font></b>")
                ).build();

        Page<Comment> comments = elasticsearchTemplate.queryForPage(queryBuilder, Comment.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                Iterator<SearchHit> iterator = hits.iterator();
                List<Comment> list = new ArrayList<>();
                while (iterator.hasNext()) {
                    SearchHit searchHit = iterator.next();
                    //取高亮结果
                    String content = searchHit.getHighlightFields().get("content").getFragments()[0].toString();

                    Comment comment = new Comment();
                    comment.setContent(content);
                    comment.setFileId(Integer.parseInt(searchHit.getSourceAsMap().get("fileId").toString()));
                    comment.setTime(Long.parseLong(searchHit.getSourceAsMap().get("time").toString()));
                    comment.setId(Integer.parseInt(searchHit.getSourceAsMap().get("id").toString()));
                    //将实体类放入集合中
                    list.add(comment);
                }
                AggregatedPage aggregatedPage = new AggregatedPageImpl(list, pageable, hits.getTotalHits());
                return aggregatedPage;
            }
        });
        List<Comment> commentList = comments.getContent();
        Map<Integer, List<Comment>> collect = commentList.stream().collect(Collectors.groupingBy(Comment::getFileId));
        List<OwnFile> results = new ArrayList<>();
        for (Integer key : collect.keySet()) {
            OwnFile fileById = ownFileService.getFileById(key);
            fileById.setComments(collect.get(key));
            results.add(fileById);
        }
        return results;
    }
}
