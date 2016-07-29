package ru.library.DAO.impl;

import com.google.gson.Gson;
import ru.library.DAO.iDAO;
import ru.library.Entity.Book;
import ru.library.Entity.News;
import ru.library.Entity.User;
import ru.library.ToolsUserInterface.LogF;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by atarasevich on 22.07.16.
 */
public class NewsDAOimpl implements iDAO {
    Connection connection;
    Statement statement;
    LogF logF;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////Соединение с БД//////////////////////////////////////////////////////
    @Override
    public void connectionToBD() {
        ////////////////////////////Получение параметров для подключения к БД///////////////////////////////////////////
        Properties properties = CommonMetodForDAO.getPropertyOfTheFile("Заглушка");

        ////////////////////////////Получение connection с БД///////////////////////////////////////////////////////////
        connection = CommonMetodForDAO.getConnection(
                properties.getProperty("driver.name"),
                properties.getProperty("db.URL"),
                properties.getProperty("db.USERNAME"),
                properties.getProperty("db.PASS")
        );
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////Закрываем соединение с БД/////////////////////////////////////////////////
    @Override
    public void disconnectWithBD() {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException var3) { this.logF.writeLog("Connect is not close!"); var3.printStackTrace(); }
            this.logF.writeLog("Disconnect OK!");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////Получение информации о новосятх из БД///////////////////////////////////////////
    @Override
    public String queryAllRecord() {

        ArrayList<News> news = new ArrayList<News>();

        try {
            statement = connection.createStatement();
            String zapros = "SELECT * FROM db_library.news";
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
    public boolean getValue(String name) {
        return false;
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
}
