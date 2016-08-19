package ru.library;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.library.DAO.impl.DBCoonectionPoll;

import java.util.HashMap;

/**
 * Created by atarasevich on 19.08.16.
 */
@Ignore
public class DBConnectionPoolTest {
    private final static Logger loggerTest = Logger.getLogger(DBConnectionPoolTest.class);
    private static HashMap<String,String> map = new HashMap<String,String>();
    static {
        map.put("CPmaxConnection", "10");
        map.put("driver", "com.mysql.jdbc.Driver");
        map.put("bd_name", "db_library");
        map.put("URL", "jdbc:mysql://localhost:3306/");
        map.put("USERNAME", "root");
        map.put("PASS", "");
    }

    @Before
    public void beforStartTest() {
        loggerTest.info("--------------------------------------------------------------------------------------------------------------------------------------");
        loggerTest.info("Test DBConnectionPoolTest - Start");
    }

    @Test
    public void getConnectionTest(){
        //Инициализируем БД и ConnectionPool
        loggerTest.info("Входные параметры для создания ConnectionPool : " + map);
        DBCoonectionPoll.getInstance(map.get("URL") + map.get("bd_name"),map.get("USERNAME"), map.get("PASS"), map.get("driver"), Integer.parseInt(map.get("CPmaxConnection")));

    }

    @After
    public void afterStartTest() {
        loggerTest.info("Test DBConnectionPoolTest - Finish");
    }

}
