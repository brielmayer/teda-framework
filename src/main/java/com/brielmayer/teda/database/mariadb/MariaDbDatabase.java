package com.brielmayer.teda.database.mariadb;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.model.Header;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MariaDbDatabase extends BaseDatabase {

    public MariaDbDatabase(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void truncateTable(String tableName) {
        executeQuery("TRUNCATE TABLE `%s`;", tableName);
    }

    @Override
    public void dropTable(String tableName) {
        executeQuery("DROP TABLE IF EXISTS `%s`;", tableName);
    }

    @Override
    public void insertRow(String tableName, Map<String, Object> row) throws SQLException {
        String query = "INSERT INTO %s(%s) VALUES (%s)";

        // "?" * header size
        List<String> placeHolder = Collections.nCopies(row.keySet().size(), "?");
        query = String.format(query, tableName, String.join(",", row.keySet()), String.join(",", placeHolder));

        executeRow(query, row);
    }

    @Override
    public List<Map<String, Object>> select(String tableName, List<Header> headers) {
        String query = "SELECT %s FROM %s";
        query = String.format(query, headers.stream().map(Header::getName).collect(Collectors.joining(",")), tableName);
        return queryForList(query);
    }
}
