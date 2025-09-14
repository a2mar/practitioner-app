package com.practitioner.backend;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {

    private static final String IMAGE_VERSION = "postgres:16";
    private static PostgresTestContainer container;

    private PostgresTestContainer() {
        super(DockerImageName.parse(IMAGE_VERSION));
        this.withDatabaseName("testdb");
        this.withUsername("postgres");
        this.withPassword("postgres");
    }

    public static PostgresTestContainer getInstance() {
        if (container == null) {
            container = new PostgresTestContainer();
            container.start();
        }
        return container;
    }

    @Override
    public void stop() {
        // do nothing, JVM handles shutdown
    }
}
