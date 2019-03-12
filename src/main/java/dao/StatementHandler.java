package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The functional interface sets the designated parameter to SQL.
 *
 * @author Andrey Akulich
 */
@FunctionalInterface
public interface StatementHandler {

    /**
     * Sets the designated parameter to SQL.
     *
     * @param statement an object that represents a precompiled SQL statement
     * @throws SQLException if a database access error occurs
     */
    void setStatement(PreparedStatement statement) throws SQLException;
}
