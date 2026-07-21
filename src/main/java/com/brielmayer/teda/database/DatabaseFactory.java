package com.brielmayer.teda.database;

import com.brielmayer.teda.exception.TedaException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ServiceLoader;

public final class DatabaseFactory {

    public static BaseDatabase createDatabase(final DataSource dataSource) throws TedaException {
        final String productName = resolveProductName(dataSource);

        final ServiceLoader<DatabaseType> loader = ServiceLoader.load(DatabaseType.class);
        for (final DatabaseType databaseType : loader) {
            if (databaseType.handles(productName)) {
                return databaseType.createDatabase(dataSource);
            }
        }

        throw TedaException.builder()
                .appendMessage("No supported database type found for database product \"%s\"", productName)
                .build();
    }

    private static String resolveProductName(final DataSource dataSource) {
        try (final Connection connection = dataSource.getConnection()) {
            return connection.getMetaData().getDatabaseProductName();
        } catch (final SQLException e) {
            throw TedaException.builder()
                    .appendMessage("Unable to determine the database product from the DataSource")
                    .cause(e)
                    .build();
        }
    }
}
