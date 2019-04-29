package com.bst.pidms.controller;

import com.bst.pidms.entity.Catalog;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.entity.User;
import com.bst.pidms.esmapper.EsCatalogMapper;
import com.bst.pidms.service.CatalogService;
import com.bst.pidms.service.OwnFileService;
import com.bst.pidms.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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


    /**
     * 遍历目录树
     *
     * @param cid
     * @return
     */
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

    /**
     * 获取子目录和子文件
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "catalogcontent", method = RequestMethod.GET)
    public Map<String, Object> getCatalog(@RequestParam("id") Integer id) {
        List<Catalog> contactsByParentId = catalogService.getContactsByParentId(id);
        List<OwnFile> byCatalog = ownFileService.getByCatalog(id);
        Map<String, Object> map = new HashMap<>();
        map.put("catalogs", contactsByParentId);
        map.put("files", byCatalog);
        return map;
    }

    /**
     * 新增目录
     *
     * @param pId
     * @param name
     * @return
     */
    @RequestMapping(value = "createcatalog", method = RequestMethod.POST)
    public Map<String, Object> createCatalog(@RequestParam("pid") Integer pId, @RequestParam("name") String name) {
        Map<String, Object> map = new HashMap<>();
        Integer userId = SessionUtil.getInstance().getIdNumber();
        if (userId == -1) {
            map.put("success", false);
            return map;
        }
        Integer integer = catalogService.addCatalog(userId, pId, name);
        map.put("success", true);
        map.put("id", integer);
        return map;
    }

    /**
     * 删除目录
     * i
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "deletecatalog", method = RequestMethod.POST)
    public Map<String, Object> deleteCatalog(@RequestParam("id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        catalogService.delCatalog(id);
        map.put("success", true);
        return map;
    }


}
