package ru.iteco.fmh.test.extensions;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class ClearDatabaseExtension implements BeforeEachCallback {

/**
Thanks to SpringExtension#getApplicationContext, access any bean of type Flyway
- which is the default for Spring Boot & Flyway integration, for  multiple
datasources and multiple Flyway beans????,
you may need to adjust this extension to your needs.
**/
    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Flyway flyway = SpringExtension.getApplicationContext(extensionContext).getBean(Flyway.class);
        flyway.clean();
        flyway.migrate();
    }
}
