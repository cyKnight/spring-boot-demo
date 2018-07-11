package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import java.sql.SQLException;

/**
 *  MyBatis基础配置
 * Created by user on 2017/9/20.
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.druid.initialSize}")
    private int initialSize;

    @Value("${spring.druid.minIdle}")
    private int minIdle;

    @Value("${spring.druid.maxActive}")
    private int maxActive;

    @Value("${spring.druid.maxWait}")
    private int maxWait;

    @Value("${spring.druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.druid.validationQuery}")
    private String validationQuery;

    @Value("${spring.druid.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.druid.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.druid.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.druid.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.druid.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.druid.filters}")
    private String filters;

    @Value("{spring.druid.connectionProperties}")
    private String connectionProperties;

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
//      return new org.apache.tomcat.jdbc.pool.DataSource();
//    }

      @Bean
      @ConfigurationProperties(prefix = "spring.datasource")
      public DruidDataSource dataSource() {
          DruidDataSource druidDataSource = new DruidDataSource();
          druidDataSource.setUrl(dbUrl);
          druidDataSource.setUsername(username);
          druidDataSource.setPassword(password);
          druidDataSource.setDriverClassName(driverClassName);

          //configuration
          druidDataSource.setInitialSize(initialSize);
          druidDataSource.setMinIdle(minIdle);
          druidDataSource.setMaxActive(maxActive);
          druidDataSource.setMaxWait(maxWait);
          druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
          druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
          druidDataSource.setValidationQuery(validationQuery);
          druidDataSource.setTestWhileIdle(testWhileIdle);
          druidDataSource.setTestOnBorrow(testOnBorrow);
          druidDataSource.setTestOnReturn(testOnReturn);
          druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
          druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
          try{
              druidDataSource.setFilters(filters);
          }catch (SQLException e){
              e.printStackTrace();
          }
          return druidDataSource;
      }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        try {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSource());
            bean.setTypeAliasesPackage("com.example.demo.model");

            //添加XML目录
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            bean.setMapperLocations(resolver.getResources("classpath*:/mybatis/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        ServletRegistrationBean registrationBean
                = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        registrationBean.addInitParameter("allow", "127.0.0.1");
        registrationBean.addInitParameter("deny", "192.168.31.234");
        registrationBean.addInitParameter("loginUsername", "admin");
        registrationBean.addInitParameter("loginPassword", "123456");
        registrationBean.addInitParameter("resetEnable", "false");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean druidWebStatViewFilter() {
        FilterRegistrationBean registrationBean
                = new FilterRegistrationBean(new WebStatFilter());
        registrationBean.addInitParameter("urlPatterns", "/*");
        registrationBean.addInitParameter("exclusions",
                "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        return registrationBean;
    }
}
