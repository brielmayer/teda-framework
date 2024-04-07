package com.brielmayer.teda.database;

import com.brielmayer.teda.util.LinkedCaseInsensitiveMap;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

@UtilityClass
public final class ResultSetMapper {

    public Map<String, Object> mapResultSet(final ResultSet resultSet) throws SQLException {
        final ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        final int columnCount = resultSetMetaData.getColumnCount();
        final Map<String, Object> mapOfColValues = createColumnMap(columnCount);

        for (int i = 1; i <= columnCount; ++i) {
            final String key = getColumnKey(resultSetMetaData, i);
            final Object obj = getColumnValue(resultSet, i);
            mapOfColValues.put(key, obj);
        }

        return mapOfColValues;
    }

    private Map<String, Object> createColumnMap(final int columnCount) {
        return new LinkedCaseInsensitiveMap<>(columnCount);
    }

    private String getColumnKey(final ResultSetMetaData resultSetMetaData, final int index) throws SQLException {
        String name = resultSetMetaData.getColumnLabel(index);
        if (name == null || name.isEmpty()) {
            name = resultSetMetaData.getColumnName(index);
        }

        return name;
    }

    private Object getColumnValue(final ResultSet rs, final int index) throws SQLException {
        return rs.getObject(index);
    }
}