package main.java.com.example.bookstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Profile("prod")
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        // Make sure URL starts with jdbc:postgresql://
        String jdbcUrl = url;
        if (!jdbcUrl.startsWith("jdbc:")) {
            jdbcUrl = "jdbc:" + jdbcUrl;
        }

        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("org.postgresql.Driver");

        return dataSource;
    }
}