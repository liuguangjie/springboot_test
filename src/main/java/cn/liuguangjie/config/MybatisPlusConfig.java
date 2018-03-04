package cn.liuguangjie.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author ms.liu
 * ~~Email liuguangj@dingtalk.com
 * @Date 2018-03-03 下午5:51
 */
@Configuration
@MapperScan(basePackages = {"cn.liuguangjie.mapper"})
public class MybatisPlusConfig {

    @Autowired
    private DataSourceProperties properties;


    public MybatisPlusConfig() {
        System.out.println(properties+ " ==========================================------");
    }
    @Bean
    public DataSource singleDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        properties.config(druidDataSource);
        return druidDataSource;
    }
}
