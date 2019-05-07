package com.bst.pidms;

import com.bst.pidms.dao.CommentMapper;
import com.bst.pidms.dao.LabelMapper;
import com.bst.pidms.dao.OwnFileMapper;
import com.bst.pidms.entity.Catalog;
import com.bst.pidms.entity.Comment;
import com.bst.pidms.entity.Label;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.esmapper.EsCatalogMapper;
import com.bst.pidms.esmapper.EsCommentMapper;
import com.bst.pidms.esmapper.EsFileMapper;
import com.bst.pidms.esmapper.EsLabelMapper;
import com.bst.pidms.service.*;
import com.google.common.collect.Lists;
import org.elasticsearch.ElasticsearchSecurityException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.jodconverter.DocumentConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest()
public class PidmsApplicationTests {


    @Autowired
    UserService userService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    EsFileMapper esFileMapper;

    @Autowired
    OwnFileService ownFileService;

    @Autowired
    CatalogService catalogService;

    @Autowired
    OwnFileMapper ownFileMapper;

    @Autowired
    LabelMapper labelMapper;

    @Autowired
    EsCatalogMapper esCatalogMapper;

    @Autowired
    EsLabelMapper esLabelMapper;

    @Autowired
    EsCommentMapper esCommentMapper;

    @Autowired
    CommentMapper commentMapper;


    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @javax.annotation.Resource
    private DocumentConverter documentConverter;

    @Test
    public void ES() {
        elasticsearchTemplate.createIndex(OwnFile.class);
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("name", "银行革命"));
        System.out.println(QueryBuilders.matchQuery("name", "银行革命").toString());
        System.out.println(System.currentTimeMillis());

        Page<OwnFile> search = esFileMapper.search(queryBuilder.build());
        System.out.println(System.currentTimeMillis());
        OwnFile ownFile = search.getContent().get(0);


        OwnFile ownFile1 = ownFileMapper.selectByPrimaryKey(33);

        search.getContent().add(ownFile1);

        Set<OwnFile> zz = new HashSet<>(search.getContent());

        System.out.println(zz.size());
    }

    @Test
    public void addSelective() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.multiMatchQuery("大街上的狗夏天自然", "tag", "keyword"));
        queryBuilder.withQuery(boolQueryBuilder);
        Iterable<OwnFile> search = esFileMapper.search(queryBuilder.build());
        List<OwnFile> ownFiles = Lists.newArrayList(search);
        for (OwnFile ownFile : ownFiles) {
            System.out.println(ownFile.getId());
        }

    }

    @Test
    public void addIndex() {
//        elasticsearchTemplate.createIndex(Comment.class);
//        List<Comment> commentList = commentMapper.selectAll();
//        esCommentMapper.saveAll(commentList);
//        elasticsearchTemplate.createIndex(Catalog.class);
//        List<Catalog> all = catalogService.getAll();
//        esCatalogMapper.saveAll(all);
//        elasticsearchTemplate.createIndex(OwnFile.class);
        List<OwnFile> all1 = ownFileService.getAll();
        esFileMapper.saveAll(all1);
//        elasticsearchTemplate.createIndex(Label.class);
//        List<Label> labels = labelMapper.selectAll();
//        esLabelMapper.saveAll(labels);
    }

    @Test
    public void zz() {
        List<OwnFile> all = ownFileService.getAll();
        for (OwnFile ownFile : all) {
            String tag = ownFile.getKeyword();
            String[] split = tag.split(" ");
            String join = String.join("|", split);
            ownFile.setKeyword(join);
            ownFileService.updateFile(ownFile);
        }
    }

}


