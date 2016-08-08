package ru.library.UserInterface;

import com.google.gson.JsonObject;
import ru.library.Entity.Book;
import ru.library.Factory.FactoryService;
import ru.library.Services.Services;
import ru.library.ToolsUserInterface.LogF;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by atarasevich on 19.07.16.
 */
public class ServletDeleteBook extends HttpServlet {
    LogF logF;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////POST (/delete_book)//////////////////////////////////////////////////
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /////////////////////////////////Задаем формат запроса и ответа/////////////////////////////////////////////////
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        ///////////////////////////////////Получем информацию от клиента////////////////////////////////////////////////
        JsonObject parametriRequest = CommonMetodForUI.getParametersOfTheReguestGET(req.getParameterMap());
        //////////////////////////////////Получем информацию из сервисов////////////////////////////////////////////////
        //Проверяем авторизовался ли пользователь
        boolean statusUser = true; //false - сейчас не проверяется авторизация
        /*if(parametriRequest.has("login")) {
            Services user = FactoryService.getService("User");
            statusUser = user.inspectionElement(parametriRequest.get("login").toString());
        }*/
        //Удаляем книгу
        boolean flagDeleteBook = false;
        if(statusUser == true) {
            Services book = FactoryService.getService("Book");

            flagDeleteBook = book.deleteElement(String.valueOf(parametriRequest.get("book_id")));
            if (flagDeleteBook) {
                logF.writeLog(">ServletDeleteBook: Запись изменена");
            }
        }
    }
}
