package com.bst.pidms.controller;

import com.bst.pidms.entity.Catalog;
import com.bst.pidms.entity.Comment;
import com.bst.pidms.entity.Label;
import com.bst.pidms.esmapper.EsCatalogMapper;
import com.bst.pidms.esmapper.EsFileMapper;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.enums.FileType;
import com.bst.pidms.service.OwnFileService;
import com.bst.pidms.utils.SessionUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @Author: BST
 * @Date: 2019/4/19 19:12
 */
@RestController
@Slf4j
public class ElasticsearchController {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    EsFileMapper esFileMapper;

    @Autowired
    EsCatalogMapper esCatalogMapper;

    @Autowired
    OwnFileService ownFileService;

    @RequestMapping(value = "search"/* , method = RequestMethod.POST */)
    public Map<String, Object> search(@RequestParam("way") Integer id, @RequestParam("text") String content) {
        log.info("way:{},text:{}", id, content);
        Map<String, Object> map = new HashMap<>();
        List<OwnFile> list = null;
        switch (id) {
            case 1:
                // 路径搜索
                List<Catalog> catalogs = pathSearch(content);
                List<Label> labels = labelSearch(content);
                map.put("CATALOG", catalogs);
                map.put("INSIGHT", labels);
                break;
            case 2:
                // 内容搜索
                list = infoSearch(content);
                break;
            case 3:
                // 关键词标签搜索
                list = keytagSearch(content);
                break;
            case 4:
                // 评论搜索
                list = commentSearch(content);
                break;
            case 5:
                // 名称搜索
                list = nameSearch(content);
                break;
            case 6:
                // 不限
                list = allSearch(content);
                map.put("CATALOG", pathSearch(content));
                map.put("INSIGHT", labelSearch(content));
                break;
        }
        if (list != null) {
            Map<Integer, List<OwnFile>> collect = list.stream().collect(Collectors.groupingBy(OwnFile::getCategory));
            collect.forEach((k, v) -> map.put(FileType.fileEnum(k).name(), v));
        }
        return map;
    }

    public List<OwnFile> keytagSearch(String content) {
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        bqb.must(QueryBuilders.termQuery("userId", SessionUtil.getInstance().getIdNumber()));
        bqb.must(QueryBuilders.multiMatchQuery(content, "keyword", "tag"));

        SearchQuery queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(bqb)
//                .withQuery(QueryBuilders.termQuery("userId", SessionUtil.getInstance().getIdNumber()))
//                .withQuery(QueryBuilders.multiMatchQuery(content, "keyword", "tag"))
//                .withPageable(PageRequest.of(0, 5))
                .withHighlightFields(new HighlightBuilder.Field("keyword")
                                .preTags("<b><font style='color:#F73634'>").postTags("</font></b>")
                        , new HighlightBuilder.Field("tag")
                                .preTags("<b><font style='color:#F73634'>").postTags("</font></b>")
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
                        keyword = searchHit.getSourceAsMap().get("keyword").toString();
                    } else {
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

    public List<OwnFile> nameSearch(String content) {
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        bqb.must(QueryBuilders.termQuery("userId", SessionUtil.getInstance().getIdNumber()));
        bqb.must(QueryBuilders.queryStringQuery(content).defaultField("name"));

        SearchQuery queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(bqb)
//                .withQuery(QueryBuilders.queryStringQuery(content).defaultField("name"))
                .withPageable(PageRequest.of(0, 5))
                .withHighlightFields(new HighlightBuilder.Field("name")
                        .preTags("<b><font style='color:#F73634'>").postTags("</font></b>")
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
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        bqb.must(QueryBuilders.termQuery("userId", SessionUtil.getInstance().getIdNumber()));
        bqb.must(QueryBuilders.queryStringQuery(content).defaultField("name"));

        SearchQuery queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(bqb)
//                .withQuery(QueryBuilders.queryStringQuery(content).defaultField("name"))
                .withPageable(PageRequest.of(0, 5))
                .withHighlightFields(new HighlightBuilder.Field("name")
                        .preTags("<b><font style='color:#F73634'>").postTags("</font></b>")
                ).build();
        Page<Catalog> catalogs = elasticsearchTemplate.queryForPage(queryBuilder, Catalog.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                Iterator<SearchHit> iterator = hits.iterator();
                List<Catalog> list = new ArrayList<>();
                while (iterator.hasNext()) {
                    SearchHit searchHit = iterator.next();
                    //取高亮结果
                    String name = searchHit.getHighlightFields().get("name").getFragments()[0].toString();
                    //将高亮结果放入实体类中
                    Catalog catalog = new Catalog();
                    catalog.setName(name);
                    catalog.setId(Integer.parseInt(searchHit.getSourceAsMap().get("id").toString()));
                    catalog.setUserId(Integer.parseInt(searchHit.getSourceAsMap().get("userId").toString()));
                    catalog.setPid(Integer.parseInt(searchHit.getSourceAsMap().get("pid").toString()));
                    //将实体类放入集合中
                    list.add(catalog);
                }
                AggregatedPage aggregatedPage = new AggregatedPageImpl(list, pageable, hits.getTotalHits());
                return aggregatedPage;
            }
        });
        return catalogs.getContent();

    }

    public List<Label> labelSearch(String content) {
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        bqb.must(QueryBuilders.termQuery("userId", SessionUtil.getInstance().getIdNumber()));
        bqb.must(QueryBuilders.queryStringQuery(content).defaultField("name"));

        SearchQuery queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(bqb)
//                .withQuery(QueryBuilders.queryStringQuery(content).defaultField("name"))
//                .withPageable(PageRequest.of(0, 5))
                .withHighlightFields(new HighlightBuilder.Field("name")
                        .preTags("<b><font style='color:#F73634'>").postTags("</font></b>")
                ).build();
        Page<Label> labels = elasticsearchTemplate.queryForPage(queryBuilder, Label.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                Iterator<SearchHit> iterator = hits.iterator();
                List<Label> list = new ArrayList<>();
                while (iterator.hasNext()) {
                    SearchHit searchHit = iterator.next();
                    //取高亮结果
                    String name = searchHit.getHighlightFields().get("name").getFragments()[0].toString();
                    //将高亮结果放入实体类中
                    Label label = new Label();
                    label.setName(name);
                    label.setId(Integer.parseInt(searchHit.getSourceAsMap().get("id").toString()));
                    label.setUserId(Integer.parseInt(searchHit.getSourceAsMap().get("userId").toString()));
                    label.setInsight(Boolean.valueOf(searchHit.getSourceAsMap().get("insight").toString()));
                    //将实体类放入集合中
                    list.add(label);
                }
                AggregatedPage aggregatedPage = new AggregatedPageImpl(list, pageable, hits.getTotalHits());
                return aggregatedPage;
            }
        });
        return labels.getContent();
    }

    public List<OwnFile> infoSearch(String content) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.multiMatchQuery(content, "info"));
        boolQueryBuilder.must(QueryBuilders.termQuery("userId", SessionUtil.getInstance().getIdNumber()));

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
                        .preTags("<b><font style='color:#F73634'>").postTags("</font></b>")
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

    public List<OwnFile> allSearch(String content) {
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        bqb.must(QueryBuilders.termQuery("userId", SessionUtil.getInstance().getIdNumber()));
        bqb.must(QueryBuilders.multiMatchQuery(content, "keyword", "tag", "name", "info"));

        SearchQuery queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(bqb)
                .withHighlightFields(new HighlightBuilder.Field("keyword")
                                .preTags("<b><font style='color:#F73634'>").postTags("</font></b>")
                        , new HighlightBuilder.Field("tag")
                                .preTags("<b><font style='color:#F73634'>").postTags("</font></b>")
                        , new HighlightBuilder.Field("name")
                                .preTags("<b><font style='color:#F73634'>").postTags("</font></b>")
//                        , new HighlightBuilder.Field("info")
//                                .preTags("<b><font style='color:#F73634'>").postTags("</font></b>")
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
                    String info = null;
                    String name = null;
                    //取高亮结果
                    if (searchHit.getHighlightFields().get("keyword") == null) {
                        keyword = searchHit.getSourceAsMap().get("keyword").toString();
                    } else {
                        keyword = searchHit.getHighlightFields().get("keyword").getFragments()[0].toString();
                    }
                    if (searchHit.getHighlightFields().get("tag") == null) {
                        tag = searchHit.getSourceAsMap().get("tag").toString();
                    } else {
                        tag = searchHit.getHighlightFields().get("tag").getFragments()[0].toString();
                    }
//                    if (searchHit.getHighlightFields().get("info") == null) {
//                        info = searchHit.getSourceAsMap().get("info").toString();
//                    } else {
//                        info = searchHit.getHighlightFields().get("info").getFragments()[0].toString();
//                    }
                    if (searchHit.getHighlightFields().get("name") == null) {
                        name = searchHit.getSourceAsMap().get("name").toString();
                    } else {
                        name = searchHit.getHighlightFields().get("name").getFragments()[0].toString();
                    }

                    //将高亮结果放入实体类中
                    OwnFile entity = new OwnFile();
                    entity.setId(Integer.parseInt(searchHit.getSourceAsMap().get("id").toString()));
                    entity.setUserId(Integer.parseInt(searchHit.getSourceAsMap().get("userId").toString()));
                    entity.setUploadTime(Long.parseLong(searchHit.getSourceAsMap().get("uploadTime").toString()));
                    entity.setServerTime(Long.parseLong(searchHit.getSourceAsMap().get("serverTime").toString()));
                    entity.setSize(Long.parseLong(searchHit.getSourceAsMap().get("size").toString()));
                    entity.setCategory(Integer.parseInt(searchHit.getSourceAsMap().get("category").toString()));
                    entity.setCatalogId(Integer.parseInt(searchHit.getSourceAsMap().get("catalogId").toString()));
                    entity.setUrl(searchHit.getSourceAsMap().get("url").toString());
                    entity.setViews(Integer.parseInt(searchHit.getSourceAsMap().get("views").toString()));
                    entity.setScale(Integer.parseInt(searchHit.getSourceAsMap().get("scale").toString()));
                    entity.setAttention(Byte.valueOf(searchHit.getSourceAsMap().get("attention").toString()));
                    entity.setCollection(Byte.valueOf(searchHit.getSourceAsMap().get("collection").toString()));
                    entity.setDownloads(Integer.parseInt(searchHit.getSourceAsMap().get("downloads").toString()));
                    entity.setRecycle(Byte.valueOf(searchHit.getSourceAsMap().get("recycle").toString()));
                    entity.setInfo(searchHit.getSourceAsMap().get("info").toString());

                    entity.setName(name);
                    entity.setKeyword(keyword);
                    entity.setTag(tag);
                    //将实体类放入集合中
                    list.add(entity);
                }
                AggregatedPage aggregatedPage = new AggregatedPageImpl(list, pageable, hits.getTotalHits());
                return aggregatedPage;
            }
        });
        List<OwnFile> filesContent1 = ownFiles.getContent();
        List<OwnFile> filesContent2 = commentSearch(content);
        for (OwnFile ownFile : filesContent1) {
            for (OwnFile file : filesContent2) {
                if (ownFile.equals(file)) ownFile.setComments(file.getComments());
            }
        }
        // 集合差集
        List<OwnFile> reduce = filesContent2.stream().filter(item -> !filesContent1.contains(item)).collect(toList());
        List<OwnFile> results = new ArrayList<>(filesContent1);
        if (reduce.size() > 0) results.addAll(reduce);
        return results;
    }

}
