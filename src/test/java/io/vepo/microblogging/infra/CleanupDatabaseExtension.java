package io.vepo.microblogging.infra;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.test.junit.callback.QuarkusTestAfterTestExecutionCallback;
import io.quarkus.test.junit.callback.QuarkusTestMethodContext;

public class CleanupDatabaseExtension implements QuarkusTestAfterTestExecutionCallback {
    private static final Logger logger = LoggerFactory.getLogger(CleanupDatabaseExtension.class);

    @Override
    public void afterTestExecution(QuarkusTestMethodContext context) {
        logger.info("Extension classloader {}", this.getClass().getClassLoader());
        TestContainerPostgreResource.ACTIVE_DATABASE.get()
                .ifPresent(info -> {
                    try {
                        logger.info("Cleaning database... context={}", context.getTestInstance());
                        try (var conn = DriverManager.getConnection(info.url(), info.username(), info.password())) {
                            var dbmd = conn.getMetaData();
                            try (var tables = dbmd.getTables(null, null, "%", new String[] { "TABLE" })) {
                                while (tables.next()) {
                                    String tableName = tables.getString("TABLE_NAME");
                                    logger.info("Cleaning table! table={}", tableName);
                                    var rowsDeleted = conn.createStatement().executeUpdate("DELETE FROM " + tableName);
                                    logger.info("Rows {} deleted from {}", rowsDeleted, tableName);
                                }
                            }
                        }
                        logger.info("Database clean!");
                    } catch (SQLException sqle) {
                        logger.error("Error cleaning database!", sqle);
                        throw new RuntimeException(sqle);
                    }
                });

    }

}
