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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = {"com.rlc.midServer.modules.mes.dao"}, sqlSessionFactoryRef = "mesDBSqlSessionFactory")
public class DataSourceConfig_MesDB {

    @Primary // 表示这个数据源是默认数据源, 这个注解必须要加，因为不加的话spring将分不清楚那个为主数据源（默认数据源）
    @Bean("mesDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mesdb") //读取application.yml中的配置参数映射成为一个对象
    public DataSource getmesDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean("mesDBSqlSessionFactory")
    public SqlSessionFactory mesDBSqlSessionFactory(@Qualifier("mesDataSource") DataSource dataSource) throws Exception {
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
        // mapper的xml形式文件位置必须要配置，不然将报错：no statement （这种错误也可能是mapper的xml中，namespace与项目的路径不一致导致）
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mappings/modules/mes/*.xml"));
        return bean.getObject();
    }
    /*
     * @methodDesc: 功能描述:(test2 事物管理)
     */
//    @Bean(name = "mesTransactionManager")
//    public DataSourceTransactionManager mesTransactionManager(@Qualifier("mesDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
    @Primary
    @Bean("mesSqlSessionTemplate")
    public SqlSessionTemplate mesSqlSessionTemplate(@Qualifier("mesDBSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}