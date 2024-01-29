package org.example.config;

import com.zaxxer.hikari.HikariConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import(JobConfiguration.class)
public class DBConfig {

    @Bean
    @ConfigurationProperties(prefix = "db.cp")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }




}
