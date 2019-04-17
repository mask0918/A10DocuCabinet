package com.bst.pidms.controller;

import com.bst.pidms.entity.Catalog;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.service.CatalogService;
import com.bst.pidms.service.OwnFileService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @Author: BST
 * @Date: 2019/4/17 9:47
 */
@Slf4j
@RestController
public class CatalogController {

    @Autowired
    CatalogService catalogService;

    @Autowired
    OwnFileService ownFileService;

    @RequestMapping(value = "catalogtree", method = RequestMethod.GET)
    public Catalog recursive(Integer cid) {
        cid = 1;
        Catalog catalog = recursiveTree(cid);
        return catalog;
    }

    public Catalog recursiveTree(Integer cid) {
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

    @RequestMapping(value = "catalogcontent", method = RequestMethod.GET)
    public Map<String, Object> getCatalog(@RequestParam("id") Integer id) {
        List<Catalog> contactsByParentId = catalogService.getContactsByParentId(id);
        List<OwnFile> byCatalog = ownFileService.getByCatalog(id);
        Map<String, Object> map = new HashMap<>();
        map.put("catalogs", contactsByParentId);
        map.put("files", byCatalog);
        return map;
    }

}
