package ru.library;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static org.junit.Assert.fail;

/**
 * Created by atarasevich on 19.08.16.
 */
public class ListenerTest {
    private final static Logger loggerTest = Logger.getLogger(ListenerTest.class);
    private Properties properties = new Properties();

    static {
        /*
        //Конструкция по динамическому добавлению аппендера для log4j
        try {
            Appender appender = new FileAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n"), "test_logs/Test_log - LibraryBook.log");
            loggerTest.addAppender(appender);
            loggerTest.info("Appender" + appender);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        HashMap<String,String> hashMapProp = new HashMap<String,String>();
        hashMapProp.put("CPmaxConnection", "10");
        hashMapProp.put("driver", "com.mysql.jdbc.Driver");
        hashMapProp.put("bd_name", "db_library");
        hashMapProp.put("URL", "jdbc:mysql://localhost:3306/");
        hashMapProp.put("USERNAME", "root");
        hashMapProp.put("PASS", "");
    }

    @Before
    public void beforStartTest() {
        loggerTest.info("--------------------------------------------------------------------------------------------------------------------------------------");
        loggerTest.info("Test InitListener - Start");
    }

    //Тестируем наличие файла проперти и необходимых настроек для инициализации приложения
    @Test
    public void getPropertyTest(){
        //Узнаем корневую папку размещенного проекта и направляем считать файл со свойствами
        String path=System.getProperty("user.dir");
        path += "\\src\\resource\\app.properties";
        loggerTest.info("Path file (app.properties) - " + path);

        //Пробуем загрузить Файл с настройками
        FileInputStream fileInputStream = null;
        Properties properties = new Properties();
        try {
            fileInputStream = new FileInputStream(path);
            properties.load(fileInputStream);
        } catch (IOException e) {
            loggerTest.error("Файл с property не найден или не может быть прочитан!");
            fail("Файл с property не найден!");
            e.printStackTrace();
        }

        //Проверка необходимых настроек для инициализации приложения
        if(properties.getProperty("CP.maxConnection") == null){
            loggerTest.error("В файле property отсутствует параметр - CP.maxConnection");
            fail("В файле property отсутствует параметр - CP.maxConnection");
            }
        if(properties.getProperty("driver.name") == null){
            loggerTest.error("В файле property отсутствует параметр - driver.name");
            fail("В файле property отсутствует параметр - driver.name");
            }
        if(properties.getProperty("db.name") == null){
            loggerTest.error("В файле property отсутствует параметр - db.name");
            fail("В файле property отсутствует параметр - db.name");
            }
        if(properties.getProperty("db.URL") == null){
            loggerTest.error("В файле property отсутствует параметр - db.URL");
            fail("В файле property отсутствует параметр - db.URL");
            }
        if(properties.getProperty("db.USERNAME") == null){
            loggerTest.error("В файле property отсутствует параметр - db.USERNAME");
            fail("В файле property отсутствует параметр - db.USERNAME");
            }
        if(properties.getProperty("db.PASS") == null){
            loggerTest.error("В файле property отсутствует параметр - db.PASS");
            fail("В файле property отсутствует параметр - db.PASS");
            }

        //Если все настройки присутствуют выводим их
        HashMap<String,String> hashMapProp2 = new HashMap<String,String>();
        hashMapProp2.put("CP.maxConnection",properties.getProperty("CP.maxConnection"));
        hashMapProp2.put("driver.name",properties.getProperty("driver.name"));
        hashMapProp2.put("db.name",properties.getProperty("db.name"));
        hashMapProp2.put("db.URL",properties.getProperty("db.URL"));
        hashMapProp2.put("db.USERNAME",properties.getProperty("db.USERNAME"));
        hashMapProp2.put("db.PASS",properties.getProperty("db.PASS"));
        loggerTest.info("Property: " + hashMapProp2);
    }

    @After
    public void afterStartTest() {
        loggerTest.info("Test InitListener - Finish");
    }
}
