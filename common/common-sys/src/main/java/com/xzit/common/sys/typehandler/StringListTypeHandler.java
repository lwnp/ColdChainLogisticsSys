package com.xzit.common.sys.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class StringListTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        // Convert List to a comma-separated string and store it in the database
        ps.setString(i, String.join(",", parameter));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // Get the column value from the ResultSet
        String value = rs.getString(columnName);
        // Check if the value is null
        if (value != null) {
            // Split the value and return the result as a List
            return Arrays.asList(value.split(","));
        } else {
            // If the value is null, return null or an empty List, depending on your requirements
            return null; // Or return an empty List: return Collections.emptyList();
        }
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // Get the column value from the ResultSet
        String value = rs.getString(columnIndex);
        // Check if the value is null
        if (value != null) {
            // Split the value and return the result as a List
            return Arrays.asList(value.split(","));
        } else {
            // If the value is null, return null or an empty List, depending on your requirements
            return null; // Or return an empty List: return Collections.emptyList();
        }
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // Get the column value from the CallableStatement
        String value = cs.getString(columnIndex);
        // Check if the value is null
        if (value != null) {
            // Split the value and return the result as a List
            return Arrays.asList(value.split(","));
        } else {
            // If the value is null, return null or an empty List, depending on your requirements
            return null; // Or return an empty List: return Collections.emptyList();
        }
    }
}