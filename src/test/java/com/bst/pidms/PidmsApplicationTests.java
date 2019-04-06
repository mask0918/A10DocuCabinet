package com.bst.pidms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bst.pidms.dao.BindRolePermissionMapper;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.service.OwnFileService;
import com.bst.pidms.service.PermissionService;
import com.bst.pidms.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest()
public class PidmsApplicationTests {

    @Autowired
    BindRolePermissionMapper mapper;

    @Autowired
    UserService userService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    Resource resource;

    @Autowired
    OwnFileService ownFileService;


    @Test
    public void getFile() {
        OwnFile ownFile = ownFileService.getFileById(1);
        String keyword = ownFile.getKeyword();
        List<String> strings = JSONObject.parseArray(keyword, String.class);
        for (String string : strings) {
            System.out.println(string);
        }

    }


//    @Test
//    public void insert(){
//        User user = userService.getUserById(6);
////        Permission per = permissionService.getPerByName("上传文档");
//        resource.save(user);
//
//    }


//    @Test
//    public void find() {
//
//        System.out.println(resource.findById(1));
//
//        QueryBuilder termQueryBuilder = QueryBuilders.termQuery("username", "小贝");
//        Iterable<User> search = resource.search(termQueryBuilder);
//        ArrayList<User> users = Lists.newArrayList(search);
//        System.out.println("size:" + users.size());
//        for (User user : users) {
//            System.out.println(user);
//        }
//    }

}
