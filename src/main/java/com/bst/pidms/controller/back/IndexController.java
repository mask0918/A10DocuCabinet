package com.bst.pidms.controller.back;

import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.service.HistoryService;
import com.bst.pidms.service.OwnFileService;
import com.bst.pidms.service.impl.OwnFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    OwnFileService ownFileService;

    @Autowired
    HistoryService historyService;

    //    登录页面
    @RequestMapping(value = "/")
    public String index(Map<String, Object> map) {
//        List<User> userList = userService.getUserList();
//        map.put("list", userList);
        return "welcome";
    }

    //    登录流程
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Map<String, Object> map, HttpSession httpSession) {
        if ("admin".equals(username) && "123456".equals(password)) {
            httpSession.setAttribute("loginUser", username);
            httpSession.setMaxInactiveInterval(-1);
            return "index";
        } else {
            map.put("msg", "用户名密码错误!");
            return "welcome";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getIndex() {
        return "index";
    }

    @RequestMapping(value = "oplog")
    public String getOpLog(Map<String, Object> map) {
        map.put("list", historyService.getAll());
        return "history";
    }

    @RequestMapping(value = "taboo")
    public String min() {
        return "sentiment";
    }

    @RequestMapping(value = "documanage")
    public String fileinfo(Map<String, Object> map) {
        List<OwnFile> all = ownFileService.getAll();
        List<String> types = new ArrayList<>();
        for (OwnFile ownFile : all) {
            switch (ownFile.getCategory()) {
                case 1:
                    types.add("<b><font style='color:#2F753B'>NLP语言内容洞见</font></b>");
                    break;
                case 2:
                    types.add("<b><font style='color:#C46642'>图像内容洞见</font></b>");
                    break;
                case 3:
                    types.add("<b><font style='color:#FC3049'>音频内容洞见</font></b>");
                    break;
                case 4:
                    types.add("<b><font style='color:#2686A3'>视频数据挖掘</font></b>");
                    break;
                case 5:
                    types.add("<b><font style='color:#01433D'>简单分析</font></b>");
                    break;
            }
        }
        map.put("list", all);
        map.put("types", types);
        return "filelist";
    }

    @RequestMapping(value = "systemlog")
    public String sysLog(Map<String, Object> map) throws Exception {
        File file = new File("log/pidms.log");
        System.out.println(file.getAbsolutePath());
        InputStreamReader read = new InputStreamReader(
                new FileInputStream(file), "gbk");// 考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        StringBuffer sb = new StringBuffer();
        while ((lineTxt = bufferedReader.readLine()) != null) {
            if (lineTxt != "") {
                sb.append(lineTxt).append("\n");
            }
        }
        map.put("log", sb.toString());
        return "syslog";
    }

}
