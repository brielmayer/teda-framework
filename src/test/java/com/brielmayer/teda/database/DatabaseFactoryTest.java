package com.brielmayer.teda.database;

import com.brielmayer.teda.database.h2.H2Database;
import com.brielmayer.teda.exception.TedaException;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DatabaseFactoryTest {

    @Test
    public void h2DataSource_createsH2Database() {
        final JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:factoryTest;DB_CLOSE_DELAY=-1");

        final BaseDatabase database = DatabaseFactory.createDatabase(dataSource);

        assertTrue(database instanceof H2Database);
    }

    @Test
    public void unknownProduct_throwsTedaException() throws Exception {
        // a DataSource whose metadata reports a product no DatabaseType handles
        final DatabaseMetaData metaData = mock(DatabaseMetaData.class);
        when(metaData.getDatabaseProductName()).thenReturn("SomeUnsupportedDb");

        final Connection connection = mock(Connection.class);
        when(connection.getMetaData()).thenReturn(metaData);

        final DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenReturn(connection);

        assertThrows(TedaException.class, () -> DatabaseFactory.createDatabase(dataSource));
    }
}
