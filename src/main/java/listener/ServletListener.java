package listener;

import connection.DBResourceManager;
import connection.ConnectionPool;
import connection.exception.DBException;
import entity.Constants;
import entity.Membership;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class ServletListener implements ServletContextListener {

    private static final Logger LOGGER = LogManager.getLogger(ServletListener.class.getSimpleName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DBResourceManager manager = DBResourceManager.getInstance();
        String driver = manager.getString(Constants.DBKey.DRIVER);
        int poolSize = manager.getInt(Constants.DBKey.POOL_SIZE);
        try {
            Class.forName(driver);
            ConnectionPool.getInstance().init(poolSize);
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Can't setStatement db driver.", e);
            throw new DBException();
        }
        //TODO HOW TO UPLOAD?? AND WHERE. ONCE UP OR EVERYONE?
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().finish();
        System.out.println("destroy");
    }
}
