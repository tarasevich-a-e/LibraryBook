package ru.library.UserInterface;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
public class ServletStart extends HttpServlet{
    LogF logF;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////Конструктор////////////////////////////////////////////////////////
    public ServletStart() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////POST (/index.html)////////////////////////////////////////////////////
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
        //Получаем информацию о библиотеке
        Services biblio = FactoryService.getService("Biblio");
        String infoAboutBiblio = biblio.getAllElements();
        //Получаем информацию о книгах
        Services book = FactoryService.getService("Book");
        String listBook = book.getAllElements();
        //Получаем информацию о новостях
        Services news = FactoryService.getService("News");
        String listNews = news.getAllElements();
        //Проверяем авторизовался ли пользователь
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


        /*
        if(parametriRequest.has("login")) {
            Services user = FactoryService.getService("User");
             statusUser = user.inspectionElement(parametriRequest.get("login").toString());
        }
        */
        ///////////////////////////////Формируем JSON для отправки клиенту//////////////////////////////////////////////
        /*Ошибка в формате передачи, для разбора необходимо чтобы на клиенте строка распозновалась как объект JSON*/
        logF.writeLog(">ServletStart: *****************");
        logF.writeLog(">ServletStart: " + infoAboutBiblio);
        logF.writeLog(">ServletStart: " + listBook);
        logF.writeLog(">ServletStart: " + listNews);
        logF.writeLog(">ServletStart: *****************");

        String strJSON = "{\"biblio\","+ infoAboutBiblio +"," +
                "\"book\","+ listBook + "," +
                "\"news\","+ listNews + "," +
                "\"user\","+ "{\"online\",\"" + statusUser  +"\"}" +
                "}";
        /*
        //ТЕСТ
        String strJSON = "[{\"biblio\","+ "{\"history_b\":\"Библиотека\",\"adress_b\":\"Москва\",\"director_b\":\"Иванова\",\"worktime_b\":\"Рабочие дни\"}]" +"," +
                "\"book\","+ "[{\"id_b\":1,\"author_b\":\"А.С. Пушкин\",\"datecorr_b\":\"июл 19, 2016\",\"dateloadbd_b\":\"июл 19, 2016\",\"name_b\":\"Сказка о мертвой царевне\",\"release_b\":1920,\"type_b\":1},{\"id_b\":2,\"author_b\":\"Г.Х. Андерсен\",\"datecorr_b\":\"июл 19, 2016\",\"dateloadbd_b\":\"июл 19, 2016\",\"name_b\":\"Принцесса на горошине.\",\"release_b\":1960,\"type_b\":1}]" + "," +
                "\"news\","+ "[{\"id_n\":1,\"date_n\":\"июл 20, 2016\",\"text_n\":\"По итогам Конкурса\"},{\"id_n\":2,\"date_n\":\"июл 20, 2016\",\"text_n\":\"С 20 июля по 31 августа\"}]" + "," +
                "\"user\","+ "{\"online\",\"" + "true"  +"\"}" +
                "}]";
        */
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
