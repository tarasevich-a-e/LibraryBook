package ru.library.DAO.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.library.DAO.iDAO;
import ru.library.Entity.Book;
import ru.library.Entity.User;
import ru.library.Factory.FactoryService;
import ru.library.Services.Services;
import ru.library.ToolsUserInterface.LogF;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * Created by atarasevich on 22.07.16.
 */
public class UserDAOimpl implements iDAO {
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
            } catch (SQLException var3) { this.logF.writeLog(">UserDAOimpl: Connect is not close!"); var3.printStackTrace(); }
            this.logF.writeLog(">UserDAOimpl: Disconnect OK!");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////Получение информации о пользователях из БД////////////////////////////////////////
    @Override
    public String queryAllRecord() {
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////Получение статуса пользователя из БД///////////////////////////////////////////
    @Override
    public boolean getValue(String name) {
        ArrayList<User> users = new ArrayList<User>();

        try {
            statement = connection.createStatement();
            String zapros = "SELECT * FROM db_library.user WHERE login_u = " + name;
            ResultSet resultSet = statement.executeQuery(zapros);

            while(resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("id_u"),
                        resultSet.getString("dr_u"),
                        resultSet.getString("famil_u"),
                        resultSet.getString("login_u"),
                        resultSet.getString("name_u"),
                        resultSet.getString("otch_u"),
                        resultSet.getString("pass_u"),
                        resultSet.getInt("rol_u"),
                        resultSet.getString("status_u"),
                        resultSet.getByte("usercol")
                ));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }
        if(users.size() != 0) {

            logF.writeLog(">UserDAOimpl: Данные из БД статус - " + users.get(0).isStatus_u());
            if(users.get(0).isStatus_u().equals("true")){return true;}
        }
        logF.writeLog(">UserDAOimpl: БД не прочитана");
        return false;

    }

    @Override
    public boolean addElement(Object o) {
        User user = (User) o; // Ранее строка отсутствовала и в метод приходил user(после проверки удалить коммент)
        int result = 0;
        try {
            statement = connection.createStatement();
            String zapros = "INSERT INTO db_library.user (`dr_u`, `famil_u`, `login_u`, `name_u`, `otch_u`, `pass_u`, `rol_u`, `status_u`, `usercol`) " +
                    "VALUE (" +
                    user.getDr_u() +
                    "," + user.getFamil_u() +
                    "," + user.getLogin_u() +
                    "," + user.getName_u() +
                    "," + user.getOtch_u() +
                    "," + user.getPass_u() +
                    "," + user.getRol_u() +
                    ",\"" +  user.isStatus_u() + "\""+
                    "," + user.getUsercol() +
                    ")";
            result = statement.executeUpdate(zapros);
            statement.close();
        } catch (SQLException var5) { var5.printStackTrace(); }
        if(result != 0) {

            logF.writeLog(">UserDAOimpl: Добавлено записей - " + result);
            return true;
        }
        logF.writeLog(">UserDAOimpl: Запись не добавлена");
        return false;
    }

    @Override
    public boolean updateUser(String login, String pass, String status) {
        int result = 0;

        try {
            statement = connection.createStatement();
            String zapros = "UPDATE db_library.user SET status_u= \"" + status + "\"" +
                    " WHERE (" +
                    "login_u=" + login +
                    ")";
            result = statement.executeUpdate(zapros);
            statement.close();
        } catch (SQLException var5) { var5.printStackTrace(); }
        if(result != 0) {

            logF.writeLog(">UserDAOimpl: Авторизация прошла успешно!");
            if (status.equals("false")) {return false;}
            else {return true;}
        }
        logF.writeLog(">UserDAOimpl: Авторизация не прошла!");
        return false;
    }

    @Override
    public String queryRecord(Object o) {
        return null;
    }
}
