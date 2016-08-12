package ru.library.UserInterface;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import ru.library.Entity.User;
import ru.library.Factory.FactoryService;
import ru.library.Services.Services;

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
public class ServletSignIn extends HttpServlet {
    final static Logger logger = Logger.getLogger(ServletSignIn.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////Конструктор////////////////////////////////////////////////////////
    public ServletSignIn() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////POST (/signin)//////////////////////////////////////////////////////
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(">ServletSignIn: Сервлет ServletSignIn работает!");
        /////////////////////////////////Задаем формат запроса и ответа/////////////////////////////////////////////////
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        ///////////////////////////////////Получем информацию от клиента////////////////////////////////////////////////
        BufferedReader breader = null;
        try {
            breader = req.getReader();
        } catch (Exception e) {
            logger.info(">ServletSignIn: Ошибка при чтении POST-запроса");
        }
        JsonObject parametriRequest = CommonMetodForUI.getParametersOfTheReguest(breader);
        //////////////////////////////////Получем информацию из сервисов////////////////////////////////////////////////
        //Проверяем авторизовался ли пользователь
        boolean statusUser = false;
        if(parametriRequest.has("status") != false) {
            //Если параметр статус существует
            if (parametriRequest.get("status").toString().equals("true")) {
                //Делаем аутентификацию и авторизацию пользователя
                if (parametriRequest.has("login") != false && parametriRequest.has("pass") != false) {
                    Services user = FactoryService.getService("User");
                    User user_p = user.autorizationElement(parametriRequest.get("login").toString(), parametriRequest.get("pass").toString(), parametriRequest.get("status").toString());
                    if (user_p == null) {
                        //Если вернулся null - пользователя с такой парой логин и пароль нету
                        statusUser = false;
                    } else {
                        //Такой логин и пароль есть
                        int hc = user_p.getLogin_u().hashCode();
                        req.getSession().setAttribute(String.valueOf(hc), user_p);
                        Cookie cookie = new Cookie("brain",String.valueOf(hc));
                        resp.addCookie(cookie);
                        statusUser = true;
                    }
                }
            }
            if (parametriRequest.get("status").toString().equals("false")) {
                //Делаем аутентификацию и авторизацию пользователя
                if (parametriRequest.has("login") != false) {
                    Services user = FactoryService.getService("User");
                    User user_p = user.autorizationElement(parametriRequest.get("login").toString(), "", parametriRequest.get("status").toString());
                    if (user_p == null) {
                        //Если вернулся null - пользователя с таким логином не существует
                        statusUser = false;
                    } else {
                        //Такой логин есть выходим
                        int hc = user_p.getLogin_u().hashCode();
                        //Пользователь вышел, затираем авторизацию
                        req.getSession().removeAttribute(String.valueOf(hc));
                        Cookie cookie = new Cookie("brain","");
                        resp.addCookie(cookie);
                        statusUser = false;
                    }
                }

            }
        }
        ///////////////////////////////Формируем JSON для отправки клиенту//////////////////////////////////////////////
        logger.info(">ServletSignIn: statusUser = " + statusUser);

        String strJSON = "{\"user\":"+ "{\"online\":" + statusUser  +"}" +
                "}";
        Gson gson = new Gson();
        strJSON = gson.toJson(strJSON);

        ///////////////////////////////////////Ложим данные в ответ/////////////////////////////////////////////////////
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(strJSON);
        printWriter.flush();
    }
}
