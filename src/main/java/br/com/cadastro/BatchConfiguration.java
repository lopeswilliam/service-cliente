package br.com.cadastro;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class BatchConfiguration {


	
	@Value("${spring.datasource.url}")
	private String dataSourceUrl;
	
	@Value("${spring.datasource.username}")
	private String dataSourceUserName;
	
	@Value("${spring.jpa.driverClassName}")
	private String dataSourceDriver;
	
	
	public void setDataSource(DataSource dataSource) {
        dataSource = null;
    }
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
    	
   	
    	HikariConfig dataSourceConfig = new HikariConfig();
    	dataSourceConfig.setDriverClassName(this.dataSourceDriver);
    	dataSourceConfig.setJdbcUrl(this.dataSourceUrl);
    	dataSourceConfig.setUsername("root");
    	dataSourceConfig.setPassword("admin");
    	dataSourceConfig.setMaximumPoolSize(10);
    	
    	return new HikariDataSource(dataSourceConfig);
    }

	@Bean
	public NamedParameterJdbcTemplate jdbcNamedTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

}
