package com.bst.pidms.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author: BST
 * @Date: 2019/4/21 9:47
 */
@Configuration
public class ElasticSearchConfig {
    /**
     * 防止netty的bug
     * java.lang.IllegalStateException: availableProcessors is already set to [4], rejecting [4]
     */
    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}
