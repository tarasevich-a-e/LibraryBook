package ru.library.DAO.impl;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by atarasevich on 22.07.16.
 */
public class CommonMetodForDAO {
    final static Logger logger = Logger.getLogger(CommonMetodForDAO.class);

    public static Properties getPropertyOfTheFile(String faleName) {
        //FileInputStream fileInputStream;
        Properties properties = new Properties();
                //try {
        //fileInputStream = new FileInputStream("src/recource/app.properties");
        //properties.load(fileInputStream);
        properties.setProperty("db.URL","jdbc:mysql://localhost:3306/db_library"); //удалить после подключения файла проперти
        properties.setProperty("db.USERNAME","root");                              //удалить после подключения файла проперти
        properties.setProperty("db.PASS","");                                      //удалить после подключения файла проперти
        properties.setProperty("driver.name","com.mysql.jdbc.Driver");             //удалить после подключения файла проперти
        /*} catch (IOException e) {
            logger.writeLog("Файл property не найден IOException;");
            e.printStackTrace();
        }*/

        return properties;
    }

    public static Connection getConnection(String driver, String URL, String USERNAME, String PASS) {
        Connection connection = null;
        try {
            Class.forName(driver).newInstance();
            Properties properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASS);
            properties.setProperty("characterEncoding","UTF-8");
            connection = DriverManager.getConnection(URL, properties);
        } catch (InstantiationException | IllegalAccessException | SQLException | ClassNotFoundException var3) {
            logger.info("Connect ERROR!");
            var3.printStackTrace();
        }
        logger.info("Connect OK!");

        return connection;
    }
}
