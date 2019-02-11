package listener;

import connection.DBResourceManager;
import connection.ConnectionPool;
import connection.DBKey;
import connection.exception.ConnectionException;
import connection.exception.DBInitException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletListener implements ServletContextListener {

    private static final Logger LOGGER = LogManager.getLogger(ServletListener.class.getSimpleName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DBResourceManager manager = DBResourceManager.getInstance();
        String driver = manager.getString(DBKey.DRIVER);
        int poolSize = manager.getInt(DBKey.POOL_SIZE);
        try {
            Class.forName(driver);
            ConnectionPool.getInstance().init(poolSize);
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Can't set db driver.", e);
            throw new DBInitException();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().finish();
        System.out.println("destroy");
    }
}
