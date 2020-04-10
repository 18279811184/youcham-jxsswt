package io.youcham.datasources;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置多数据源
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017/8/19 0:41
 */
@Configuration
public class DynamicDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.db1.druid.first")
    public DataSource firstDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.db1.druid.second")
    public DataSource secondDataSource(){
        return DruidDataSourceBuilder.create().build();
    }
    
    @Bean
    @ConfigurationProperties("spring.datasource.db1.druid.mega")
    public DataSource megaDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.db2.druid.first")
    public DataSource db2FirstDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.db2.druid.two")
    public DataSource db2TwoDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource firstDataSource, DataSource secondDataSource,DataSource megaDataSource,
                                        DataSource db2FirstDataSource,DataSource db2TwoDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceNames.FIRST, firstDataSource);
        targetDataSources.put(DataSourceNames.SECOND, secondDataSource);
        targetDataSources.put(DataSourceNames.MEGA, megaDataSource);
        targetDataSources.put(DataSourceNames.DB2_FIRST, db2FirstDataSource);
        targetDataSources.put(DataSourceNames.DB2_TWO, db2TwoDataSource);
        return new DynamicDataSource(firstDataSource, targetDataSources);
    }
}
