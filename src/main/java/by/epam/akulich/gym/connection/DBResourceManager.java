package by.epam.akulich.gym.connection;

import by.epam.akulich.gym.connection.exception.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 *
 * @author Andrey Akulich
 */
public class DBResourceManager {

    /**
     * A constant has an instance of {@link Logger} to logs this class.
     */
    private static final Logger LOGGER = LogManager.getLogger(DBResourceManager.class.getSimpleName());

    /**
     * Name of file which has information about database initialization.
     */
    private static final String DATABASE = "database.properties";

    /**
     * This class represents initialization-on-demand holder idiom for {@link DBResourceManager}.
     */
    private static class DBResourceManagerHolder{
        static final DBResourceManager INSTANCE = new DBResourceManager();
    }

    /**
     * Properties of file.
     */
    private Properties properties;

    /**
     * Instantiates new DBResourceManager.
     */
    private DBResourceManager() {
        init();
    }

    /**
     * @return {@link DBResourceManager} instance
     */
    public static DBResourceManager getInstance() {
        return DBResourceManagerHolder.INSTANCE;
    }

    /**
     * Load properties for database.
     */
    private void init() {
        properties = new Properties();
        ClassLoader classLoader = DBResourceManager.class.getClassLoader();
        try (InputStream stream = Objects.requireNonNull(classLoader.getResourceAsStream(DATABASE))) {
            properties.load(stream);
        } catch (IOException e) {
            LOGGER.fatal("ResourceManager IO trouble.", e);
            throw new DBException();
        }
    }

    /**
     * Returns a {@code String} value of property.
     *
     * @param key a key property
     * @return a {@code String} value of property.
     */
    public String getString(String key) {
        return properties.getProperty(key);
    }

    /**
     * Returns a {@code int} value of property.
     *
     * @param key a key property
     * @return a {@code int} value of property
     */
    public int getInt(String key) {
        String string = properties.getProperty(key);
        return Integer.parseInt(string);
    }
}
