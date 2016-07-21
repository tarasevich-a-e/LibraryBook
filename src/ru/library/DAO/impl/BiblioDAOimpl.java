package ru.library.DAO.impl;

import com.google.gson.Gson;
import ru.library.DAO.BiblioDAO;
import ru.library.Entity.Biblio;
import ru.library.ToolsUserInterface.LogF;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by atarasevich on 20.07.16.
 */
public class BiblioDAOimpl implements BiblioDAO {
    Connection connection;
    Statement statement;
    LogF logF = new LogF();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////Соединение с БД//////////////////////////////////////////////////////
    @Override
    public void connectionToBD() {
        logF.createLog();
        ////////////////////////////Получение параметров для подключения к БД///////////////////////////////////////////
        //FileInputStream fileInputStream;
        Properties properties = new Properties();

        //try {
            //fileInputStream = new FileInputStream("src/recource/app.properties");
            //properties.load(fileInputStream);
            properties.setProperty("db.URL","jdbc:mysql://localhost:3306/db_library"); //удалить после подключения файла проперти
            properties.setProperty("db.USERNAME","root");                              //удалить после подключения файла проперти
            properties.setProperty("db.PASS","");                                      //удалить после подключения файла проперти
            properties.setProperty("driver.name","com.mysql.jdbc.Driver");             //удалить после подключения файла проперти

            String URL = properties.getProperty("db.URL");
            String USERNAME = properties.getProperty("db.USERNAME");
            String PASS = properties.getProperty("db.PASS");
            String driver = properties.getProperty("driver.name");

            logF.writeLog("Данные из файла property считаны: URL = " + URL + "; USERNAME = " + USERNAME + "; PASS = " + PASS + ";");

        /*} catch (IOException e) {
            logF.writeLog("Файл property не найден IOException;");
            e.printStackTrace();
        }*/
        ////////////////////////////Получение connection с БД///////////////////////////////////////////////////////////
        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(URL, USERNAME, PASS);
            //statement = connection.createStatement();
        } catch (InstantiationException | IllegalAccessException | SQLException | ClassNotFoundException var3) {
            var3.printStackTrace();
        }
        logF.writeLog("Connect OK!");
        //logF.writeLog(System.getProperty("user.dir"));
        //logF.writeLog(getClass().getClassLoader().getResourceAsStream("app.properties").toString());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////Закрываем соединение с БД/////////////////////////////////////////////////
    @Override
    public void disconnectWithBD() {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException var3) { var3.printStackTrace(); }
            this.logF.writeLog("Disconnect OK!");
            this.logF.close();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////Получение информации о библиотеке из БД//////////////////////////////////////////
    @Override
    public String queryInfoAboutBiblio() {
        ArrayList<Biblio> biblio = new ArrayList<Biblio>();

        try {
            statement = connection.createStatement();
            String zapros = "SELECT * FROM db_library.biblio";
            ResultSet resultSet = statement.executeQuery(zapros);

            while(resultSet.next()) {
                biblio.add(new Biblio(resultSet.getString("history_b"), resultSet.getString("adress_b"), resultSet.getString("director_b"), resultSet.getString("worktime_b")));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

        Gson gson1 = new Gson();
        return gson1.toJson(biblio);
    }
}
