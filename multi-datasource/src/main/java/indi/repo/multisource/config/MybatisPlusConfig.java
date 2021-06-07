package indi.repo.multisource.config;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/7
 * @desc:
 */

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * (1)配置分页插件
 * (2)在这个配置文件中配置多数据源配置
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public Interceptor[] paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        Interceptor[] interceptors = new PaginationInterceptor[1];
        interceptors[0] = paginationInterceptor;
        return interceptors;
    }

    //初始化数据源first
    @Bean(name = "first")
    @ConfigurationProperties(prefix = "spring.datasource.druid.first")
    public DataSource firstDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    //初始化数据源second
    @Bean(name = "second")
    @ConfigurationProperties(prefix = "spring.datasource.druid.second")
    public DataSource secondDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 动态数据源配置
     * @return
     */
    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("first") DataSource first,
                                           @Qualifier("second") DataSource second) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        //放入数据源first
        targetDataSources.put(DBTypeEnum.first.getValue(), first);
        //放入数据源second
        targetDataSources.put(DBTypeEnum.second.getValue(), second);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        //设置默认数据源first
        dynamicDataSource.setDefaultTargetDataSource(first);
        return dynamicDataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        // 在这里注入sqlSessionFactory要使用的数据源，而这里获取到的数据源正是我们实现的AbstractRoutingDataSource类所返回的数据源
        sqlSessionFactory.setDataSource(multipleDataSource(firstDataSource(), secondDataSource()));
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);
        //添加分页功能
        sqlSessionFactory.setPlugins(paginationInterceptor());
        return sqlSessionFactory.getObject();
    }
}
