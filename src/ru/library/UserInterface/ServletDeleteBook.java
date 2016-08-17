package ru.library.UserInterface;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by atarasevich on 19.07.16.
 */
public class ServletDeleteBook extends HttpServlet {
    final static Logger logger = Logger.getLogger(ServletDeleteBook.class);
    final static Logger loggerDAO = Logger.getLogger("file3");
    final static Logger loggerThread = Logger.getLogger("file4");
    private int count = 0;
    private JsonObject parametriRequest = null;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////POST (/delete_book)//////////////////////////////////////////////////
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        count++;
        logger.info("------------------------------------------------------------------------------------------------");
        loggerDAO.info("------------------------------------------------------------------------------------------------");
        loggerThread.info("------------------------------------------------------------------------------------------------");
        loggerDAO.info("Удаление книги");
        loggerThread.info("Удаление книги");
        /////////////////////////////////Задаем формат запроса и ответа/////////////////////////////////////////////////
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        ///////////////////////////////////Получем информацию от клиента////////////////////////////////////////////////
        logger.info("Извлекаем переданные данные из request'a");

        try {
            parametriRequest = CommonMetodForUI.getParametersOfTheReguestGET(req.getParameterMap());
        } catch (Exception e) {
            logger.error("Ошибка при чтении POST-запроса");
        }
        logger.info("Данные из request'a извлечены");
        loggerDAO.info("Входящий запрос: " + parametriRequest);
        //////////////////////////////////Получем информацию из сервисов////////////////////////////////////////////////
        ///////////////////////////////////////////
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
        loggerDAO.info("Пользователь авторизован: " + (statusUser? "ДА":"НЕТ"));
        ///////////////////////////////////////////
        //Удаляем книгу
                ExecutorService executorService = Executors.newCachedThreadPool();
                Future<String> inf_book = null;
                if(statusUser == true) {
                    logger.info("Удаляем книгу");

                    //Открываем поток для получения информации о книгах
                        inf_book = executorService.submit(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            loggerThread.info("Book: thread N" + count + " - start");
                            long start = System.currentTimeMillis();
                            Services book = FactoryService.getService("Book");
                            String listBook = book.deleteElement(String.valueOf(parametriRequest.get("book_id")));
                            if (listBook == null){
                                logger.warn("Информация о книгах не получена");
                                loggerDAO.warn("Информация о книгах не получена");
                            }
                            else {
                                logger.info("Информация о книгах получена");
                                loggerDAO.info("Информация о книгах получена");
                            }
                            long finish = System.currentTimeMillis();
                            loggerThread.info("Book: thread N" + count + " finish. ( work time - " + (finish - start) + ")");
                            return listBook;
                        }
                    });
                }
        //Достаем информацию из потоков
                while (!inf_book.isDone()) {}

                loggerThread.info("All thread finish. Read sending info.");
                String listBook = "";

                try {
                    listBook = inf_book.get();
                } catch (Exception e) {
                    loggerThread.info("Exeption - информация потеряна!");
                    e.printStackTrace();
                }
        ///////////////////////////////Формируем JSON для отправки клиенту//////////////////////////////////////////////
        logger.info("Готовим информацию для передачи клиенту");
        String strJSON = "{\"book\":"+ listBook +  "," +
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
        loggerDAO.info("------------------------------------------------------------------------------------------------");
    }
}
