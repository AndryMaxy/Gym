package connection;

import connection.exception.DBInitException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;

public class DBResourceManager {

    private static final Logger LOGGER = LogManager.getLogger(DBResourceManager.class.getSimpleName());
    private static final String DATABASE = "database.properties";
    private static final DBResourceManager INSTANCE = new DBResourceManager();
    private Properties properties;

    private DBResourceManager() {
        init();
    }

    public static DBResourceManager getInstance() {
        return INSTANCE;
    }

    private void init() {
        properties = new Properties();
        ClassLoader classLoader = DBResourceManager.class.getClassLoader();
        try (InputStream stream = Objects.requireNonNull(classLoader.getResourceAsStream(DATABASE))) {
            properties.load(stream);
        } catch (IOException e) {
            LOGGER.fatal("ResourceManager IO trouble.", e);
            throw new DBInitException();
        }
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }

    public int getInt(String key) {
        String string = properties.getProperty(key);
        return Integer.parseInt(string);
    }
}
