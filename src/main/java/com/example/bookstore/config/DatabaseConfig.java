package com.example.bookstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Profile("prod")
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
        // Parse the Render-provided URL to construct a proper JDBC URL
        String jdbcUrl;

        if (databaseUrl != null && !databaseUrl.isEmpty()) {
            if (databaseUrl.contains("@")) {
                // Format: postgresql://username:password@hostname:port/database
                // We need to extract parts and rebuild it properly
                try {
                    // Extract username:password
                    String credentials = databaseUrl.substring(databaseUrl.indexOf("//") + 2, databaseUrl.indexOf("@"));

                    // Extract host:port/database
                    String hostDb = databaseUrl.substring(databaseUrl.indexOf("@") + 1);

                    // Build proper JDBC URL
                    jdbcUrl = "jdbc:postgresql://" + hostDb;

                    // Create datasource with separate username and password
                    HikariDataSource dataSource = new HikariDataSource();
                    dataSource.setJdbcUrl(jdbcUrl);

                    // Set credentials separately
                    String[] userPass = credentials.split(":");
                    if (userPass.length == 2) {
                        dataSource.setUsername(userPass[0]);
                        dataSource.setPassword(userPass[1]);
                    } else {
                        dataSource.setUsername(username);
                        dataSource.setPassword(password);
                    }

                    dataSource.setDriverClassName("org.postgresql.Driver");
                    dataSource.setMaximumPoolSize(5);
                    dataSource.setMinimumIdle(1);
                    System.out.println("Created datasource with URL: " + jdbcUrl);
                    return dataSource;
                } catch (Exception e) {
                    System.err.println("Error parsing database URL: " + e.getMessage());
                }
            }

            // Fallback - just add jdbc: prefix
            jdbcUrl = databaseUrl.startsWith("jdbc:") ? databaseUrl : "jdbc:" + databaseUrl;
        } else {
            throw new IllegalStateException("Database URL is required but not provided");
        }

        // Default approach
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }
}