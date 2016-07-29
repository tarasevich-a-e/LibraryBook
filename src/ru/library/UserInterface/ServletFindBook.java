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

/**
 * Created by atarasevich on 19.07.16.
 */
public class ServletFindBook extends HttpServlet {
    LogF logF;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////Конструктор////////////////////////////////////////////////////////
    public ServletFindBook() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////GET (/find_book)/////////////////////////////////////////////////////
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /////////////////////////////////Задаем формат запроса и ответа/////////////////////////////////////////////////
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        ///////////////////////////////////Получем информацию от клиента////////////////////////////////////////////////
        JsonObject parametriRequest = CommonMetodForUI.getParametersOfTheReguestGET(req.getParameterMap());
        //////////////////////////////////Получем информацию из сервисов////////////////////////////////////////////////
        //Ищем книгу
        Services book = FactoryService.getService("Book");

        Book el_book = new Book(
                parametriRequest.get("book_id").getAsInt(),
                String.valueOf(parametriRequest.get("book_author")),
                String.valueOf(parametriRequest.get("book_datecorr")),
                String.valueOf(parametriRequest.get("book_dateloadbd")),
                String.valueOf(parametriRequest.get("book_name")),
                parametriRequest.get("book_release").getAsInt(),
                parametriRequest.get("rasdel").getAsInt()
        );
        String listBook = book.getElements(el_book); //возможен null, если нет книг удовлетворяющих запросу
        listBook = listBook.substring(1, listBook.length()-1);
        ///////////////////////////////Формируем JSON для отправки клиенту//////////////////////////////////////////////
        logF.writeLog(">ServletFindBook: *****************");
        logF.writeLog(">ServletFindBook: listBook = " + listBook);
        logF.writeLog(">ServletFindBook: *****************");
        String strJSON = "[{\"book\","+ listBook + "}]";
        Gson gson = new Gson();
        strJSON = gson.toJson(strJSON);
        ///////////////////////////////////////Ложим данные в ответ/////////////////////////////////////////////////////
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(strJSON);
        printWriter.flush();
    }
}
