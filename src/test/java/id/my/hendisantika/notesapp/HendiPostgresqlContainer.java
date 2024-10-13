package id.my.hendisantika.notesapp;

import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Created by IntelliJ IDEA.
 * Project : Notes-App
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 5/20/24
 * Time: 07:25
 * To change this template use File | Settings | File Templates.
 */
public class HendiPostgresqlContainer extends PostgreSQLContainer<HendiPostgresqlContainer> {
    private static final String IMAGE_VERSION = "postgres:17-alpine3.20";
    private static HendiPostgresqlContainer container;

    private HendiPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    public static HendiPostgresqlContainer getInstance() {
        if (container == null) {
            container = new HendiPostgresqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

}
