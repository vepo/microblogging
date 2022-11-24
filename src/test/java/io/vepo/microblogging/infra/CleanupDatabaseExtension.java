package io.vepo.microblogging.infra;

import static io.vepo.microblogging.infra.TestContainerPostgreResource.ACTIVE_DATABASE;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.test.junit.callback.QuarkusTestAfterTestExecutionCallback;
import io.quarkus.test.junit.callback.QuarkusTestMethodContext;

public class CleanupDatabaseExtension implements QuarkusTestAfterTestExecutionCallback {
    private static final Logger logger = LoggerFactory.getLogger(CleanupDatabaseExtension.class);

    @Override
    public void afterTestExecution(QuarkusTestMethodContext context) {
        ACTIVE_DATABASE.get().ifPresent(info -> {
            try {
                logger.info("Cleaning database... {}::{}", context.getTestInstance().getClass().getName(),
                        context.getTestMethod().getName());
                try (var conn = DriverManager.getConnection(info.url(), info.username(), info.password())) {
                    var dbmd = conn.getMetaData();
                    try (var tables = dbmd.getTables(null, null, "%", new String[] { "TABLE" })) {
                        while (tables.next()) {
                            String tableName = tables.getString("TABLE_NAME");
                            logger.info("Selecting table({})...", tableName);
                            var rowsDeleted = conn.createStatement().executeUpdate("TRUNCATE " + tableName + " RESTART IDENTITY;");
                            logger.info("Rows {} deleted from {}", rowsDeleted, tableName);
                        }
                    }
                }
                logger.info("Database cleaned!");
            } catch (SQLException sqle) {
                logger.error("Error cleaning database!", sqle);
                throw new TestSetupException("Fail to cleanup database!", sqle);
            }
        });

    }

}
