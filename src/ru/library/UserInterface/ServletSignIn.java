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
    final static Logger loggerDAO = Logger.getLogger("file3");

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////POST (/signin)//////////////////////////////////////////////////////
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("------------------------------------------------------------------------------------------------");
        loggerDAO.info("------------------------------------------------------------------------------------------------");
        loggerDAO.info("Авторизация пользователя");
        /////////////////////////////////Задаем формат запроса и ответа/////////////////////////////////////////////////
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        ///////////////////////////////////Получем информацию от клиента////////////////////////////////////////////////
        logger.info("Извлекаем переданные данные из request'a");
        BufferedReader breader = null;
        try {
            breader = req.getReader();
        } catch (Exception e) {
            logger.error("Ошибка при чтении POST-запроса");
        }
        JsonObject parametriRequest = CommonMetodForUI.getParametersOfTheReguest(breader);
        logger.info("Данные из request'a извлечены");
        loggerDAO.info("Входящий запрос: " + parametriRequest);
        //////////////////////////////////Получем информацию из сервисов////////////////////////////////////////////////
        logger.info("Проверяем авторизовался ли пользователь");
        //Проверяем авторизовался ли пользователь
        boolean statusUser = false;
        if(parametriRequest.has("status") != false) {
            //Если параметр статус существует
            if (parametriRequest.get("status").toString().equals("true")) {
                loggerDAO.info("Пользователь хочет авторизоваться.");
                //Делаем аутентификацию и авторизацию пользователя
                if (parametriRequest.has("login") != false && parametriRequest.has("pass") != false) {
                    logger.info("Выполняем авторизацию");
                    Services user = FactoryService.getService("User");
                    User user_p = user.autorizationElement(parametriRequest.get("login").toString(), parametriRequest.get("pass").toString(), parametriRequest.get("status").toString());
                    if (user_p == null) {
                        logger.warn("Авторизацию не удалась, такой пользователь не зарегистрирован");
                        //Если вернулся null - пользователя с такой парой логин и пароль нету
                        statusUser = false;
                    } else {
                        //Такой логин и пароль есть
                        int hc = user_p.getLogin_u().hashCode();
                        req.getSession().setAttribute(String.valueOf(hc), user_p);
                        Cookie cookie = new Cookie("brain",String.valueOf(hc));
                        resp.addCookie(cookie);
                        statusUser = true;
                        logger.info("Авторизацию удалась, регистрируем в сессии");
                    }
                }
            }
            if (parametriRequest.get("status").toString().equals("false")) {
                loggerDAO.info("Пользователь хочет выйти.");
                //Делаем аутентификацию и авторизацию пользователя
                if (parametriRequest.has("login") != false) {
                    logger.info("Выполняем выход пользователя из системы");
                    Services user = FactoryService.getService("User");
                    User user_p = user.autorizationElement(parametriRequest.get("login").toString(), "", parametriRequest.get("status").toString());
                    if (user_p == null) {
                        logger.warn("Выход не произведен, такой пользователь не зарегистрирован");
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
                        logger.info("Выход удался, затираем информацию в сессии");
                    }
                }

            }
        }
        loggerDAO.info("Пользователь авторизован: " + (statusUser? "ДА":"НЕТ"));
        ///////////////////////////////Формируем JSON для отправки клиенту//////////////////////////////////////////////
        logger.info("Готовим информацию для передачи клиенту");
        String strJSON = "{\"user\":"+ "{\"online\":" + statusUser  +"}" +
                "}";
        logger.info("Информация для передачи клиенту: " + strJSON);
        Gson gson = new Gson();
        strJSON = gson.toJson(strJSON);
        logger.info("Конвертация в JSON прошла успешно");
        ///////////////////////////////////////Ложим данные в ответ/////////////////////////////////////////////////////
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(strJSON);
        printWriter.flush();
        logger.info("Данные отправлены клиенту, работа в сервлете закончена.");
        logger.info("------------------------------------------------------------------------------------------------");
        loggerDAO.info("------------------------------------------------------------------------------------------------");
    }
}
