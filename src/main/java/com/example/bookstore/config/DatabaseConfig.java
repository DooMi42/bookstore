package main.java.com.example.bookstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Profile("prod") // Only load this for 'prod', not 'prod-test'
public class DatabaseConfig {

    @Value("${SPRING_DATASOURCE_URL:#{null}}")
    private String databaseUrl;

    @Value("${SPRING_DATASOURCE_USERNAME:#{null}}")
    private String username;

    @Value("${SPRING_DATASOURCE_PASSWORD:#{null}}")
    private String password;

    @Bean
    @Primary
    public DataSource dataSource() {
        // Only create this if we have the necessary properties
        if (databaseUrl == null || username == null || password == null) {
            throw new IllegalStateException("Database connection properties not set");
        }

        String jdbcUrl = databaseUrl;
        if (jdbcUrl != null && !jdbcUrl.startsWith("jdbc:")) {
            jdbcUrl = "jdbc:" + jdbcUrl;
        }

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }
}