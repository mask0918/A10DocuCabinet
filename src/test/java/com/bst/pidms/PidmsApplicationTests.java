package com.bst.pidms;

import com.alibaba.fastjson.JSONObject;
import com.bst.pidms.dao.OwnFileMapper;
import com.bst.pidms.entity.Catalog;
import com.bst.pidms.service.CatalogService;
import com.bst.pidms.service.OwnFileService;
import com.bst.pidms.service.PermissionService;
import com.bst.pidms.service.UserService;
import org.jodconverter.DocumentConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest()
public class PidmsApplicationTests {


    @Autowired
    UserService userService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    Resource resource;

    @Autowired
    OwnFileService ownFileService;

    @Autowired
    CatalogService catalogService;

    @Autowired
    OwnFileMapper ownFileMapper;

    @javax.annotation.Resource
    private DocumentConverter documentConverter;

    public Catalog recursiveTree(int cid) {
        // 根据cid获取节点对象(SELECT * FROM tb_tree t WHERE t.cid=?)
        Catalog node = catalogService.getContactById(cid);
        // 查询cid下的所有子节点(SELECT * FROM tb_tree t WHERE t.pid=?)
        List<Catalog> childTreeNodes = catalogService.getContactsByParentId(cid);
        // 遍历子节点
        for (Catalog child : childTreeNodes) {
            Catalog n = recursiveTree(child.getId()); //递归
            node.getNodes().add(n);
        }
        return node;
    }

    @Test
    public void zzz() {
        Catalog catalog = recursiveTree(1);
        System.out.println(JSONObject.toJSONString(catalog));
    }
}


