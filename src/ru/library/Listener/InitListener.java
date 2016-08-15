package ru.library.Listener;

import org.apache.log4j.Logger;
import ru.library.DAO.impl.DBCoonectionPoll;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by atarasevich on 15.08.16.
 */
public class InitListener implements ServletContextListener {
    final static Logger logger = Logger.getLogger(InitListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("------------------------------------------------------------------------------------------------");
        logger.info("Инициализация контекста");
        //Получаем данные из файла с проперти
        logger.info("Извлекаем файнные из файла property");
        getProperty(servletContextEvent.getServletContext());
        logger.info("Из контекста достаем hashmap с property");
        HashMap<String,String> map = (HashMap<String,String>)servletContextEvent.getServletContext().getAttribute("hashMapProp");
        logger.info("Hashmap с property получен : " + map.toString());
        //Инициализируем ConnectionPool
        DBCoonectionPoll.getInstance(map.get("URL") + map.get("bd_name"),map.get("USERNAME"), map.get("PASS"), map.get("driver"), Integer.parseInt(map.get("CPmaxConnection")));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //Закрываем ConnectionPool
        DBCoonectionPoll.releasedConnection();
    }

    private void getProperty(ServletContext servletContext) {

        logger.info("Инициализируем файл с property");
        String path_config = servletContext.getRealPath("/WEB-INF/resource/app.properties");
        FileInputStream fileInputStream = null;
        Properties properties = new Properties();
        HashMap<String,String> hashMapProp = new HashMap<String,String>();

        try {
            logger.info("Перекладываем property в hashmap");
            fileInputStream = new FileInputStream(path_config);
            properties.load(fileInputStream);
            hashMapProp.put("CPmaxConnection",properties.getProperty("CP.maxConnection"));
            hashMapProp.put("driver",properties.getProperty("driver.name"));
            hashMapProp.put("bd_name",properties.getProperty("db.name"));
            hashMapProp.put("URL",properties.getProperty("db.URL"));
            hashMapProp.put("USERNAME",properties.getProperty("db.USERNAME"));
            hashMapProp.put("PASS",properties.getProperty("db.PASS"));
        } catch (FileNotFoundException e) {
            logger.error("Файл с property не найден!");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("IOException при чтении файла с property!");
            e.printStackTrace();
        }
        logger.info("Property полйчены и помещены в hashmap");
        servletContext.setAttribute("hashMapProp", hashMapProp);
    }
}
