package ru.library.DAO.impl;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.library.DAO.iDAO;
import ru.library.Entity.News;
import ru.library.Entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by atarasevich on 22.07.16.
 */
public class NewsDAOimpl implements iDAO {
    Connection connection;
    Statement statement;
    final static Logger logger = Logger.getLogger(NewsDAOimpl.class);
    final static Logger loggerDAO = Logger.getLogger("file3");

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////Соединение с БД//////////////////////////////////////////////////////
    @Override
    public void connectionToBD() {
        ////////////////////////////Получение connection с БД///////////////////////////////////////////////////////////
        logger.info("----------------------------------------------------");
        logger.info("Take me connection");
        long start = System.currentTimeMillis();
        connection = DBCoonectionPoll.getConnection();
        long finish = System.currentTimeMillis();
        logger.info("Time - " + (finish - start));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////Закрываем соединение с БД/////////////////////////////////////////////////
    @Override
    public void disconnectWithBD() {
        DBCoonectionPoll.freeConnection(connection);
        /*if(connection != null) {
            try {
                connection.close();
            } catch (SQLException var3) { this.logger.info("Connect is not close!"); var3.printStackTrace(); }
            this.logger.info("Disconnect OK!");
        }*/
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////Получение информации о новосятх из БД///////////////////////////////////////////
    @Override
    public String queryAllRecord() {

        ArrayList<News> news = new ArrayList<News>();

        try {
            statement = connection.createStatement();
            String zapros = "SELECT * FROM db_library.news";

            loggerDAO.info("Query :" + zapros);
            ResultSet resultSet = statement.executeQuery(zapros);

            while(resultSet.next()) {
                news.add(new News(
                        resultSet.getInt("id_n"),
                        resultSet.getString("date_n"),
                        resultSet.getString("text_n")
                ));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

        Gson gson1 = new Gson();
        return gson1.toJson(news);
    }

    @Override
    public ArrayList<User> getValue(String name) {
        return null;
    }

    @Override
    public boolean addElement(Object o) {
        return false;
    }

    @Override
    public boolean updateUser(String login, String pass, String status) {
        return false;
    }

    @Override
    public String queryRecord(Object o) {
        return null;
    }

    @Override
    public boolean updateElement(Object o) { return false; }

    @Override
    public boolean deleteElement(String idElement) {
        return false;
    }
}
