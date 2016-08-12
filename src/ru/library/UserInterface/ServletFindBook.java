package ru.library.UserInterface;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import ru.library.Entity.Book;
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
public class ServletFindBook extends HttpServlet {
    final static Logger logger = Logger.getLogger(ServletFindBook.class);

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
        ///////////////////////////////////////////
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
        ///////////////////////////////////////////
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
        //listBook = listBook.substring(1, listBook.length()-1);
        ///////////////////////////////Формируем JSON для отправки клиенту//////////////////////////////////////////////
        logger.info(">ServletFindBook: *****************");
        logger.info(">ServletFindBook: listBook = " + listBook);
        logger.info(">ServletFindBook: *****************");
        String strJSON = "{\"book\":"+ listBook +  "," +
                "\"user\":"+ "{\"online\":" + statusUser  +"}" +
                "}";
        Gson gson = new Gson();
        strJSON = gson.toJson(strJSON);
        ///////////////////////////////////////Ложим данные в ответ/////////////////////////////////////////////////////
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(strJSON);
        printWriter.flush();
    }
}
