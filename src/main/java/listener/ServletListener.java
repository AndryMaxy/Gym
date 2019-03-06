package listener;

import dao.connection.DBResourceManager;
import dao.connection.ConnectionPool;
import dao.connection.exception.DBException;
import entity.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This listener is used for initialize this web application and connection pool.
 *
 * @author Andrey Akulich
 */
@WebListener
public class ServletListener implements ServletContextListener {

    /**
     * Logs this class.
     */
    private static final Logger LOGGER = LogManager.getLogger(ServletListener.class.getSimpleName());

    /**
     * Initializes this web application and connection pool.
     * @param sce servlet context event of this web application
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DBResourceManager manager = DBResourceManager.getInstance();
        String driver = manager.getString(Constants.DBKey.DRIVER);
        int poolSize = manager.getInt(Constants.DBKey.POOL_SIZE);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Can't set database driver.", e);
            throw new DBException();
        }
        ConnectionPool.getInstance().init(poolSize);
    }

    /**
     * Finalize this web application and connection pool.
     * @param sce servlet context event of this web application
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().finish();
    }
}
