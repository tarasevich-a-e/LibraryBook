package ru.library.UserInterface;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
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
public class ServletStart extends HttpServlet{
    final static Logger logger = Logger.getLogger(ServletStart.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////Конструктор////////////////////////////////////////////////////////
    public ServletStart() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////POST (/index.html)////////////////////////////////////////////////////
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("------------------------------------------------------------------------------------------------");
        /////////////////////////////////////////////////Проверяем сессию///////////////////////////////////////////////
        logger.info("Получаем информацию о времени сессии по умолчанию у Tomcat'a. Timout session = " + req.getSession().getMaxInactiveInterval());
        logger.info("POST запрос принят.");
        /////////////////////////////////Задаем формат запроса и ответа/////////////////////////////////////////////////
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        ///////////////////////////////////Получем информацию от клиента////////////////////////////////////////////////
        logger.info("Извлекаем переданные данные из request'a");
        BufferedReader breader = null;
        try {
            breader = req.getReader();
        } catch (Exception e) {
            logger.info(">ServletStart: Ошибка при чтении POST-запроса");
        }
        JsonObject parametriRequest = CommonMetodForUI.getParametersOfTheReguest(breader);
        logger.info("Данные из request'a извлечены");
        //////////////////////////////////Получем информацию из сервисов////////////////////////////////////////////////
        //Получаем информацию о библиотеке
        logger.info("Получаем информацию о библиотеке");
        Services biblio = FactoryService.getService("Biblio");
        String infoAboutBiblio = biblio.getAllElements();
        //Получаем информацию о книгах
        logger.info("Получаем информацию о книгах");
        Services book = FactoryService.getService("Book");
        String listBook = book.getAllElements();
        //Получаем информацию о новостях
        logger.info("Получаем информацию о новостях");
        Services news = FactoryService.getService("News");
        String listNews = news.getAllElements();
        //Проверяем авторизовался ли пользователь
        logger.info("Проверяем авторизовался ли пользователь");
        boolean statusUser = false;
        Cookie[] mas_cook = req.getCookies();
        String find_cook = "";
        for (int i = 0; i < mas_cook.length; i++) {
            if(mas_cook[i].getName().equals("brain")){
                find_cook = mas_cook[i].getValue();
                break;
            }
        }
        if (req.getSession().getAttribute(find_cook) != null) {
            //Пользователь авторизован
            statusUser = true;
        }
        logger.info("Пользователь авторизован: " + (statusUser? "ДА":"НЕТ"));

        ///////////////////////////////Формируем JSON для отправки клиенту//////////////////////////////////////////////
        /*Ошибка в формате передачи, для разбора необходимо чтобы на клиенте строка распозновалась как объект JSON*/
        logger.info("Готовим информацию для передачи клиенту");
        String strJSON = "{\"biblio\":"+ infoAboutBiblio +"," +
                "\"book\":"+ listBook + "," +
                "\"news\":"+ listNews + "," +
                "\"user\":"+ "{\"online\":" + statusUser  +"}" +
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
    }
}
