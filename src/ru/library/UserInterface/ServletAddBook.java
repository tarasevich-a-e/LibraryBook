package ru.library.UserInterface;

import com.google.gson.Gson;
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
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by atarasevich on 19.07.16.
 */
public class ServletAddBook extends HttpServlet {
    LogF logF;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////Конструктор////////////////////////////////////////////////////////
    public ServletAddBook() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////POST (/add_book)/////////////////////////////////////////////////////
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
            logF.writeLog(">ServletAddBook: Ошибка при чтении POST-запроса");
        }
        JsonObject parametriRequest = CommonMetodForUI.getParametersOfTheReguest(breader);
        //////////////////////////////////Получем информацию из сервисов////////////////////////////////////////////////
        //Проверяем авторизовался ли пользователь
        boolean statusUser = false;
        if(parametriRequest.has("login")) {
            Services user = FactoryService.getService("User");
            statusUser = user.inspectionElement(parametriRequest.get("login").toString());
        }
        //Ищем книгу
        boolean flagAddUser = false;
        if(statusUser == true) {
            Services book = FactoryService.getService("Book");

            //Формируем дату для добавления в БД
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Book el_book = new Book(
                    -1,
                    String.valueOf(parametriRequest.get("book_author")),
                    String.valueOf(dateFormat.format(date)),
                    String.valueOf(dateFormat.format(date)),
                    String.valueOf(parametriRequest.get("book_name")),
                    parametriRequest.get("book_release").getAsInt(),
                    parametriRequest.get("rasdel").getAsInt()
            );
            flagAddUser = book.addElement(el_book);
            if (flagAddUser) {
                logF.writeLog(">ServletAddBook: Запись добавлена");
            }
        }
        ///////////////////////////////Формируем JSON для отправки клиенту//////////////////////////////////////////////
        String strJSON = "[{\"user\",{\"online\",\"" + statusUser  +"\"}," +
                "{\"book\",{\"status\",\"" + flagAddUser  +"\"}"+
                "}]";
        Gson gson = new Gson();
        strJSON = gson.toJson(strJSON);
        ///////////////////////////////////////Ложим данные в ответ/////////////////////////////////////////////////////
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(strJSON);
        printWriter.flush();

    }
}
