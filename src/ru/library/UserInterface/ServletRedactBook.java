package ru.library.UserInterface;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ru.library.Entity.Book;
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by atarasevich on 19.07.16.
 */
public class ServletRedactBook extends HttpServlet {
    LogF logF;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////Конструктор////////////////////////////////////////////////////////
    public ServletRedactBook() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////POST (/redact_book)//////////////////////////////////////////////////
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
            logF.writeLog(">ServletRedactBook: Ошибка при чтении POST-запроса");
        }
        JsonObject parametriRequest = CommonMetodForUI.getParametersOfTheReguest(breader);
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
        //Изменяем книгу
        String listBook = null;
        if(statusUser == true) {
            Services book = FactoryService.getService("Book");

            //Формируем дату для добавления в БД
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Book el_book = new Book(
                    parametriRequest.get("book_id").getAsInt(),
                    String.valueOf(parametriRequest.get("book_author")),
                    String.valueOf(dateFormat.format(date)),
                    "-1",
                    String.valueOf(parametriRequest.get("book_name")),
                    parametriRequest.get("book_release").getAsInt(),
                    parametriRequest.get("rasdel").getAsInt()
            );
            listBook = book.editElement(el_book);

        }
        ///////////////////////////////Формируем JSON для отправки клиенту//////////////////////////////////////////////
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
