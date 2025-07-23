package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The type Connessione database.
 */
public class ConnessioneDatabase {

    private static Connection instance;

        private ConnessioneDatabase() {}

    /**
     * Gets instance.
     *
     * @return the instance
     * @throws SQLException the sql exception
     */
    public static Connection getInstance() throws SQLException {
            if (instance == null || instance.isClosed()) {
                instance = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/Aeroporto", "postgres", "13012006");
            }
            return instance;
        }

}
