package ru.library.UserInterface;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ru.library.Entity.User;
import ru.library.Factory.FactoryService;
import ru.library.Services.Services;
import ru.library.ToolsUserInterface.LogF;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by atarasevich on 19.07.16.
 */
public class ServletSingUp extends HttpServlet {
    LogF logF;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////Конструктор////////////////////////////////////////////////////////
    public ServletSingUp() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////POST (/signup)//////////////////////////////////////////////////////
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /////////////////////////////////Задаем формат запроса и ответа/////////////////////////////////////////////////
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        ///////////////////////////////////Получем информацию от клиента////////////////////////////////////////////////
        BufferedReader breader = null;
        try {
            breader = req.getReader();
        } catch (Exception e) {
            logF.writeLog(">ServletStart: Ошибка при чтении POST-запроса");
        }
        JsonObject parametriRequest = CommonMetodForUI.getParametersOfTheReguest(breader);
        //////////////////////////////////Получем информацию из сервисов////////////////////////////////////////////////
        ///////////////////////////////////////////
        //Получаем информацию о пользователе
        Services user = FactoryService.getService("User");

        User user1 = new User(
                0,
                parametriRequest.get("user_dr").toString(),
                parametriRequest.get("user_f").toString(),
                parametriRequest.get("login").toString(),
                parametriRequest.get("user_n").toString(),
                parametriRequest.get("user_o").toString(),
                parametriRequest.get("pass").toString(),
                0,
                "true",
                (byte) 0
        );

        String flagAddUser = user.addElement(user1);
        if(flagAddUser.equals("true")) {
            logF.writeLog(">ServletSingUp: Запись добавлена");
        }
        ///////////////////////////////Формируем JSON для отправки клиенту//////////////////////////////////////////////
        String strJSON = "{\"user\":"+ "{\"status\":" + flagAddUser  +"}" +
                "}";
        Gson gson = new Gson();
        strJSON = gson.toJson(strJSON);
        ///////////////////////////////////////Ложим данные в ответ/////////////////////////////////////////////////////
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(strJSON);
        printWriter.flush();
        /////////////////////////////////////////Пишем лог в файл///////////////////////////////////////////////////////
        logF.writeLog(">ServletStart: Сервлет ServletStart работает!");
    }


}
