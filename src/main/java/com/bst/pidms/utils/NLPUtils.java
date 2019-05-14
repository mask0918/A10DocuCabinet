package com.bst.pidms.utils;

import com.bst.pidms.entity.reader.OfficeReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @Author: BST
 * @Date: 2019/4/18 19:40
 */
public class NLPUtils {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\BST\\Desktop\\服务外包\\第八届省赛项目\\赛题\\11-2019第八届浙江省大学生服务外包创新应用大赛_统一命题\\赛题10_自动化院_个人文档智能管理系统.docx");
        String info = OfficeReader.getInfo(file.getAbsolutePath(), "docx");
        String keywords = getKeywords("http://yzny6c.natappfree.cc/keywords", info, file.getName());
        System.out.println(keywords);
    }

    public static String getKeywords(String url, String val, String title) {
        HttpClient httpclient = HttpClientBuilder.create().build();
        try {
            HttpPost httppost = new HttpPost(url + "?content=" + URLEncoder.encode(val, "UTF-8") + "&title=" + URLEncoder.encode(title, "UTF-8"));
            httppost.addHeader("Content-type", "application/json; charset=utf-8");
            HttpResponse response = httpclient.execute(httppost);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                String result = EntityUtils.toString(resEntity);
                EntityUtils.consume(resEntity);
                return result;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
