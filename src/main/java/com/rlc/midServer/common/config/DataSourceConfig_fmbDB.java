package com.rlc.midServer.common.config;

import com.rlc.midServer.common.mapper.typeHandler.ConvertBlobTypeHandler;
import com.rlc.midServer.common.pageHelper.page.Page;
import com.rlc.midServer.common.persistence.interceptor.PaginationInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = {"com.rlc.midServer.modules.fmb.dao","com.rlc.midServer.modules.test.dao"}, sqlSessionFactoryRef = "fmbSqlSessionFactory")
public class DataSourceConfig_fmbDB {
    @Bean("fmbDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.fmbdb")
    public DataSource getmesDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("fmbSqlSessionFactory")
    public SqlSessionFactory mesDBSqlSessionFactory(@Qualifier("fmbDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        Properties properties = new Properties();
        //数据库
        properties.setProperty("jdbc.type", "oracle");
        paginationInterceptor.setProperties(properties);
        bean.setPlugins(paginationInterceptor);
        bean.setTypeAliases(Page.class);
        bean.setTypeHandlers(new ConvertBlobTypeHandler());
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mappings/modules/fmb/*.xml"));
        return bean.getObject();
    }
    /*
     * @methodDesc: 功能描述:(test2 事物管理)
     */
//    @Bean(name = "fmbTransactionManager")
//    public DataSourceTransactionManager fmbTransactionManager(@Qualifier("fmbDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
    @Bean("fmbSqlSessionTemplate")
    public SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("fmbSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}