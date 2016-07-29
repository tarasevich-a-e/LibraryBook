package ru.library.UserInterface;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.library.ToolsUserInterface.LogF;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by atarasevich on 19.07.16.
 */
public class ServletSignIn extends HttpServlet {
    LogF logF;

    public ServletSignIn() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////ТЕСТИРУЮ////////////////////////////////
        logF.writeLog("Сервлет ServletSignIn работает!");
        //Читаем тело POST
        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            BufferedReader breader = req.getReader();
            while ((line = breader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            logF.writeLog("Ошибка при чтении POST-запроса");
        }
        //Переводим тело в JSON
        Gson gson = new Gson();
        gson.toJson(sb);
        //Извлекаем данные из JSON
        JsonParser jsonParser = new JsonParser();
        Object obj = jsonParser.parse(sb.toString());
        JsonObject jsonObject = (JsonObject) obj;
        logF.writeLog("Парамметр log найден и равен " + jsonObject.get("log").toString());
        logF.writeLog("Парамметр bay найден и равен " + jsonObject.get("bay").toString());
        logF.writeLog("Парамметр privet найден и равен " + jsonObject.get("privet").toString());
        if(jsonObject.has("priiivet")){
            logF.writeLog("Парамметр priiivet найден и равен " + jsonObject.get("priiivet").toString());
        }else {
            logF.writeLog("Парамметр priiivet отсутствует!");
        }
        ///////////////////////////////ТЕСТИРУЮ////////////////////////////////
        ///////////////////////////////////////////////////////////////////////
    }
}
