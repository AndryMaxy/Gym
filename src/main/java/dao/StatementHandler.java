package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementHandler {
    void setData(PreparedStatement statement) throws SQLException;
}
