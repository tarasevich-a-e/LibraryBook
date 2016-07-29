package ru.library.UserInterface;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.library.Factory.FactoryService;
import ru.library.Services.Services;
import ru.library.ToolsUserInterface.LogF;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by atarasevich on 19.07.16.
 */
public class ServletSignIn extends HttpServlet {
    LogF logF;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////Конструктор////////////////////////////////////////////////////////
    public ServletSignIn() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////POST (/signin)//////////////////////////////////////////////////////
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logF.writeLog(">ServletSignIn: Сервлет ServletSignIn работает!");
        /////////////////////////////////Задаем формат запроса и ответа/////////////////////////////////////////////////
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        ///////////////////////////////////Получем информацию от клиента////////////////////////////////////////////////
        BufferedReader breader = null;
        try {
            breader = req.getReader();
        } catch (Exception e) {
            logF.writeLog(">ServletSignIn: Ошибка при чтении POST-запроса");
        }
        JsonObject parametriRequest = CommonMetodForUI.getParametersOfTheReguest(breader);
        //////////////////////////////////Получем информацию из сервисов////////////////////////////////////////////////
        //Проверяем авторизовался ли пользователь
        boolean statusUser = false;
        if(parametriRequest.has("login") != false && parametriRequest.has("pass") != false) {
            Services user = FactoryService.getService("User");
            statusUser = user.autorizationElement(parametriRequest.get("login").toString(),parametriRequest.get("pass").toString(), parametriRequest.get("status").toString());
        }
        ///////////////////////////////Формируем JSON для отправки клиенту//////////////////////////////////////////////
        logF.writeLog(">ServletSignIn: statusUser = " + statusUser);

        String strJSON = "[{\"user\","+ "{\"online\",\"" + statusUser  +"\"}" +
                "}]";
        Gson gson = new Gson();
        strJSON = gson.toJson(strJSON);
        ///////////////////////////////////////Ложим данные в ответ/////////////////////////////////////////////////////
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(strJSON);
        printWriter.flush();
    }
}
