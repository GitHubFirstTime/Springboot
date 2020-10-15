package com.rlc.midServer;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, DataSourceTransactionManagerAutoConfiguration.class})
//@MapperScan({"com.rlc.midServer.modules.test.dao","com.rlc.midServer.modules.mes.dao"})
public class FmbMidServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FmbMidServerApplication.class, args);
    }
}