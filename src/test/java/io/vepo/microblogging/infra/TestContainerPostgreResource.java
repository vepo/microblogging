package io.vepo.microblogging.infra;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.DevServicesContext;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class TestContainerPostgreResource
        implements QuarkusTestResourceLifecycleManager, DevServicesContext.ContextAware {
    private final static Logger logger = LoggerFactory.getLogger(TestContainerPostgreResource.class);
    private Optional<String> containerNetworkId;
    private JdbcDatabaseContainer container;

    public static record DatabaseInfo(String url, String username, String password) {
    }

    static AtomicReference<Optional<DatabaseInfo>> ACTIVE_DATABASE = new AtomicReference<>(Optional.empty());

    @Override
    public void setIntegrationTestContext(DevServicesContext context) {
        containerNetworkId = context.containerNetworkId();
    }

    @Override
    public Map<String, String> start() {
        logger.info("Initializing database container (Postgres)....");
        // start a container making sure to call withNetworkMode() with the value of
        // containerNetworkId if present
        container = new PostgreSQLContainer<>("postgres:latest").withLogConsumer(outputFrame -> {
        });

        // apply the network to the container
        containerNetworkId.ifPresent(container::withNetworkMode);

        // start container before retrieving its URL or other properties
        container.start();

        String jdbcUrl = container.getJdbcUrl();
        if (containerNetworkId.isPresent()) {
            // Replace hostname + port in the provided JDBC URL with the hostname of the
            // Docker container
            // running PostgreSQL and the listening port.
            jdbcUrl = fixJdbcUrl(jdbcUrl);
        }

        logger.info("Database ({}) initialized!", container.getImage());

        ACTIVE_DATABASE.set(Optional.of(new DatabaseInfo(jdbcUrl, container.getUsername(), container.getPassword())));

        // return a map containing the configuration the application needs to use the
        // service
        return Map.of(
                "quarkus.datasource.username", container.getUsername(),
                "quarkus.datasource.password", container.getPassword(),
                "quarkus.datasource.jdbc.url", jdbcUrl,
                "quarkus.hibernate-orm.database.generation", "drop-and-create");
    }

    private String fixJdbcUrl(String jdbcUrl) {
        // Part of the JDBC URL to replace
        String hostPort = container.getHost() + ':' + container.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT);

        // Host/IP on the container network plus the unmapped port
        String networkHostPort = container.getCurrentContainerInfo().getConfig().getHostName()
                + ':'
                + PostgreSQLContainer.POSTGRESQL_PORT;

        return jdbcUrl.replace(hostPort, networkHostPort);
    }

    @Override
    public void stop() {
        logger.info("Stopping database(Postgress)...");
        ACTIVE_DATABASE.set(Optional.empty());
        // close container
        container.stop();
        logger.info("Database stopped!");
    }
}
