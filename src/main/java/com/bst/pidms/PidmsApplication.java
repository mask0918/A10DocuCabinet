package com.bst.pidms;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication()
@MapperScan("com.bst.pidms.dao")
@EnableTransactionManagement
//@ComponentScan(basePackages = {"com.bst.pidms.*"})
public class PidmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PidmsApplication.class, args);
    }

}
