package scot.oskar.springdeed;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class PostgresConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> prostgresContainer() {
        return new PostgreSQLContainer<>("postgres:16");
    }
}
