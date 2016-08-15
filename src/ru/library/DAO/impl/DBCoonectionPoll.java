package ru.library.DAO.impl;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by atarasevich on 15.08.16.
 */
public class DBCoonectionPoll {
    final static Logger logger = Logger.getLogger(DBCoonectionPoll.class);
    private static String URL;
    private static String USERNAME;
    private static String PASS;
    private static String driver;
    private static int maxConn;
    private static ArrayList freeConnectoins;
    private static int incr_request;
    private static int incr_work_conn;

    private static DBCoonectionPoll ourInstance = null;

    public static synchronized DBCoonectionPoll getInstance(String URL, String USERNAME, String PASS, String driver, int maxConn) {
        logger.info("Initialisation ConnectionPool");
        if(ourInstance == null) {
            ourInstance = new DBCoonectionPoll(URL, USERNAME, PASS, driver, maxConn);
        }

        return ourInstance;
    }

    private DBCoonectionPoll(String URL, String USERNAME, String PASS, String driver, int maxConn) {
        logger.info("Create ConnectionPool");
        this.URL = URL;
        this.USERNAME = USERNAME;
        this.PASS = PASS;
        this.driver = driver;
        this.maxConn = maxConn;
        incr_request = 0;
        incr_work_conn = 0;
        freeConnectoins = new ArrayList();
        logger.info("Load driver for BD : " + driver);
        logger.info("Max connection for BD : " + maxConn);
        loadDrivers();
        logger.info("ConnectionPool create successfully!");
    }

    private void loadDrivers() {
        try {
            Driver driv = (Driver) Class.forName(driver).newInstance();
            DriverManager.registerDriver(driv);
            logger.info("Driver load successfully!");
        } catch (InstantiationException e) {
            logger.error("Error, driver doesn't load, InstantiationException");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error("Error, driver doesn't load, IllegalAccessException");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.error("Error, driver doesn't load, ClassNotFoundException");
            e.printStackTrace();
        } catch (SQLException e) {
            logger.error("Error, driver doesn't load, SQLException");
            e.printStackTrace();
        }
    }

    //Получение соединения
    public static synchronized Connection getConnection() {
        incr_request++;

        logger.info("Request N - " + incr_request);
        logger.info("Connection in stack - " + freeConnectoins.size() + "; Connection in work N - " + incr_work_conn);
        Connection conn = null;

        if (!freeConnectoins.isEmpty()) {
            //logger.info("Connection in stack - " + freeConnectoins.size());
            conn = (Connection) freeConnectoins.get(freeConnectoins.size() - 1);
            freeConnectoins.remove(conn);

            try {
                if(conn.isClosed()){
                    //logger.info("Connection is close, repeat get connection in stack");
                    //Рекурсия, запрашиваем заново, так как полученный connection закрыт
                    conn = getConnection();
                }
            } catch (SQLException e) {
                logger.error("Repeat get connection doesn't perform!");
                e.printStackTrace();
            }

        } else {
            //logger.info("Connection in stack - 0");
            conn = newConnection();
        }

        logger.info("Give connection of the stack");
        incr_work_conn++;
        logger.info("Connection in stack - " + freeConnectoins.size() + "; Connection in work N - " + incr_work_conn);
        return conn;
    }

    private static Connection newConnection() {
        Connection conn = null;

        Properties properties = new Properties();
        properties.setProperty("user", USERNAME);
        properties.setProperty("password", PASS);
        properties.setProperty("characterEncoding","UTF-8");

        try {
            logger.info("Create new connection");
            conn = (Connection) DriverManager.getConnection(URL, properties);
            //logger.info("Connection in stack - " + freeConnectoins.size());
        } catch (SQLException e) {
            logger.error("New connection doesn't create!");
            e.printStackTrace();
        }
        return conn;
    }
    public static void freeConnection(Connection conn) {
        if((conn !=null) && (freeConnectoins.size() <= maxConn)){
            logger.info("Free connection");
            freeConnectoins.add(conn);
            incr_work_conn--;
            logger.info("Connection in stack - " + freeConnectoins.size() + "; Connection in work N - " + incr_work_conn);
        }
    }

    public static void releasedConnection() {
        Iterator allConnection = freeConnectoins.iterator();
        logger.info("Close all connections - " + freeConnectoins.size());
        while (allConnection.hasNext()) {

            Connection conn = (Connection) allConnection.next();

            try {
                logger.info("Close connection - " + conn);
                conn.close();
            } catch (SQLException e) {
                logger.error("Connection doesn't close!");
                e.printStackTrace();
            }

        }
        freeConnectoins.clear();
    }
}
