package com.bst.pidms.controller;

import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.enums.opEnum;
import com.bst.pidms.service.HistoryService;
import com.bst.pidms.service.OwnFileService;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.poserver.Server;
import org.jodconverter.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Author: BST
 * @Date: 2019/4/16 13:41
 */
@Controller
public class PageOfficeController {
    @Resource
    DocumentConverter documentConverter;

    @Autowired
    OwnFileService ownFileService;

    @Autowired
    HistoryService historyService;

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        Server poserver = new com.zhuozhengsoft.pageoffice.poserver.Server();
        //设置PageOffice注册成功后,license.lic文件存放的目录
        poserver.setSysPath("D:\\InsightPIDMS");
        ServletRegistrationBean srb = new ServletRegistrationBean(poserver);
        srb.addUrlMappings("/poserver.zz");
        srb.addUrlMappings("/assets/js/poserver.zz");
        srb.addUrlMappings("/posetup.exe");
//        srb.addUrlMappings("/pageoffice.js");
        srb.addUrlMappings("/jquery.min.js");
        srb.addUrlMappings("/pobstyle.css");
        srb.addUrlMappings("/sealsetup.exe");
        return srb;
    }

    @RequestMapping(value = "hhh", method = RequestMethod.GET)
    public String Z() {
        return "sad";
    }

    /**
     * office online打开
     *
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView showWord(@PathVariable("id") Integer id, HttpServletRequest request, Map<String, Object> map) {
        String fileName = ownFileService.getFileById(id).getName();
        //--- PageOffice的调用代码 开始 -----
        PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
        poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");//设置授权程序servlet
        poCtrl.addCustomToolButton("保存", "Save()", 1); //添加自定义按钮
        poCtrl.addCustomToolButton("打印", "PrintFile()", 6);
        poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
        poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
        poCtrl.setSaveFilePage("/save/" + id);//设置保存的action

        String substring = fileName.substring(fileName.lastIndexOf(".") + 1);
        OpenModeType openModeType = null;

        if (substring.equals("doc") || substring.equals("docx")) openModeType = OpenModeType.docAdmin;
        if (substring.equals("xls") || substring.equals("xlsx")) openModeType = OpenModeType.xlsNormalEdit;
        if (substring.equals("ppt") || substring.equals("pptx")) openModeType = OpenModeType.pptNormalEdit;

        System.out.println(openModeType.toString());
        poCtrl.webOpen("D:\\InsightPIDMS\\zzz\\DOCUMENT\\" + fileName, openModeType, "洞见");
        poCtrl.setCaption("Insight!!!!!!!!!!!冲冲");
        map.put("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
        //--- PageOffice的调用代码 结束 -----
        ModelAndView mv = new ModelAndView("onlineedit");
//        return poCtrl.getHtmlCode("PageOfficeCtrl1");
        return mv;
    }

    /**
     * 保存office
     *
     * @param request
     * @param response
     */
    @RequestMapping("/save/{id}")
    public void saveFile(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer userId = 1;
        FileSaver fs = new FileSaver(request, response);
        //保存文件
        String fileName = ownFileService.getFileById(id).getName();
        String path = "D:\\InsightPIDMS\\zzz\\DOCUMENT\\" + fileName;
        fs.saveToFile(path);
        fs.close();
        documentConverter.convert(new File(path)).to(new File(path.substring(0, path.lastIndexOf(".")) + ".pdf")).execute();
        StringBuffer sb = new StringBuffer();
        sb.append(opEnum.EDIT.getName());
        sb.append("\"" + fileName + "\"");
        historyService.addHistory(System.currentTimeMillis(), sb.toString(), userId);
    }

}
