package kr.co.zlgoon.scheduler.config.db;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class SchedulerDbConfig {
	
	public static final String BASE_PACKAGE = "kr.co.zlgoon.scheduler.repository";
	public static final String MAPPER_XML_PATH = "classpath:sql/mysql/**/*.xml";

	@Primary
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public DataSourceProperties hosuk1DataSourceProp() {
		return new DataSourceProperties();
	}
	
	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariDataSource hosuk1HikariDataSource() {
		return hosuk1DataSourceProp().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
	
	  
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") HikariDataSource hosuk1DataSource) throws Exception {
		
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(hosuk1DataSource);
		bean.setTypeAliasesPackage("kr.co.zlgoon.scheduler.vo");
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_PATH));
		
		return bean.getObject();
		
	}
	
	@Bean(name = "sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate ( @Qualifier("sqlSessionFactory") SqlSessionFactory hosuk1SqlSessionFactory ) {
		hosuk1SqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
		return new SqlSessionTemplate(hosuk1SqlSessionFactory);
		
	}
	
	@Bean(name = "transactionManager")
	public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") HikariDataSource hosuk1DataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(hosuk1DataSource);
		
		return transactionManager;
	}
}
