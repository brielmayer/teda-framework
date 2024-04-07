package com.brielmayer.teda.database;

import com.brielmayer.teda.exception.TedaException;

import javax.sql.DataSource;
import java.util.ServiceLoader;

public final class DatabaseFactory {

    public static BaseDatabase createDatabase(final DataSource dataSource) throws TedaException {
        final ServiceLoader<DatabaseType> loader = ServiceLoader.load(DatabaseType.class);
        loader.reload();
        for (final DatabaseType implClass : loader) {
            if (implClass.handlesDataSource(dataSource)) {
                return implClass.createDatabase(dataSource);
            }
        }

        throw TedaException.builder()
                .appendMessage("No supported database type found")
                .build();
    }
}
