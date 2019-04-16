package com.bst.pidms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * @Author: BST
 * @Date: 2019/4/12 18:59
 */
@Configuration
public class SourceConfig extends WebMvcConfig {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * @Description: 对文件的路径进行配置, 创建一个虚拟路径/file/** ，即只要在<img src="/file/images/20180522/9aa64b2b-a558-421e-929c-537ff0aecdba.jpg" />便可以直接引用图片
         *这是图片的物理路径 "file:/+本地图片的地址"
         * @Date： Create in 14:08 2017/12/20
         *
         */
        registry.addResourceHandler("/testpreview/**").addResourceLocations("file:/D:/InsightPIDMS/");
        super.addResourceHandlers(registry);
    }

}
