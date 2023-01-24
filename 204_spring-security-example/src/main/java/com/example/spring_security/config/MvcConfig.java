package com.example.spring_security.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.example.spring_security")
@PropertySource("classpath:persistence-mysql.properties")
public class MvcConfig implements WebMvcConfigurer {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Environment env;
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
	}
	
	@Bean
	public DataSource securityDataSource() {
		ComboPooledDataSource ds = new ComboPooledDataSource();

		try {
			log.info("jdbc.driver: " + this.env.getProperty("jdbc.driver"));
			log.info("jdbc.url: " + this.env.getProperty("jdbc.url"));
			log.info("jdbc.user: " + this.env.getProperty("jdbc.user"));

			ds.setDriverClass(this.env.getProperty("jdbc.driver"));
			ds.setJdbcUrl(this.env.getProperty("jdbc.url"));
			ds.setUser(this.env.getProperty("jdbc.user"));
			ds.setPassword(this.env.getProperty("jdbc.password"));

			ds.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
			ds.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
			ds.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
			ds.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}

		return ds;
	}
	
	private int getIntProperty(String propName) {
		String propValue = this.env.getProperty(propName);
		return Integer.parseInt(propValue);
	}
}
