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
import java.util.concurrent.*;

/**
 * Created by atarasevich on 19.07.16.
 */
public class ServletStart extends HttpServlet{
    final static Logger logger = Logger.getLogger(ServletStart.class);
    final static Logger loggerDAO = Logger.getLogger("file3");
    final static Logger loggerThread = Logger.getLogger("file4");
    private int count = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////POST (/index.html)////////////////////////////////////////////////////
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        count++;
        logger.info("------------------------------------------------------------------------------------------------");
        loggerDAO.info("------------------------------------------------------------------------------------------------");
        loggerThread.info("------------------------------------------------------------------------------------------------");
        loggerDAO.info("Инициализация первой страницы");
        loggerThread.info("Инициализация первой страницы");
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
            logger.error("Ошибка при чтении POST-запроса");
        }
        JsonObject parametriRequest = CommonMetodForUI.getParametersOfTheReguest(breader);
        logger.info("Данные из request'a извлечены");
        loggerDAO.info("Входящий запрос: " + parametriRequest);
        //////////////////////////////////Получем информацию из сервисов////////////////////////////////////////////////
        //Получаем информацию о библиотеке
        logger.info("Получаем информацию о библиотеке");
        loggerDAO.info("Получаем информацию о библиотеке");

                    //Открываем поток для получения информации о библиотеке
                    ExecutorService executorService = Executors.newCachedThreadPool();
                    Future<String> inf_biblio = executorService.submit(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            loggerThread.info("Bioblio: thread N" + count + " - start");
                            long start = System.currentTimeMillis();
                                Services biblio = FactoryService.getService("Biblio");
                                String tmp = biblio.getAllElements();
                            long finish = System.currentTimeMillis();
                            loggerThread.info("Bioblio: thread N" + count + " finish. (work time - " + (finish - start) + ")");
                            return tmp;
                        }
                    });

        //Получаем информацию о книгах
        logger.info("Получаем информацию о книгах");
        loggerDAO.info("Получаем информацию о книгах");
                    //Открываем поток для получения информации о книгах
                    Future<String> inf_book = executorService.submit(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            loggerThread.info("Book: thread N" + count + " - start");
                            long start = System.currentTimeMillis();
                                Services book = FactoryService.getService("Book");
                                String listBook = book.getAllElements();
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

        //Получаем информацию о новостях
        logger.info("Получаем информацию о новостях");
        loggerDAO.info("Получаем информацию о новостях");
                    //Открываем поток для получения информации о новостях
                    Future<String> inf_news = executorService.submit(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            loggerThread.info("News: thread N" + count + " - start");
                            long start = System.currentTimeMillis();
                                Services news = FactoryService.getService("News");
                                String tmp = news.getAllElements();
                            long finish = System.currentTimeMillis();
                            loggerThread.info("News: thread N" + count + " finish. ( work time - " + (finish - start) + ")");
                            return tmp;
                        }
                    });

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

                    //Достаем информацию из потоков
                    while (!inf_biblio.isDone() || !inf_book.isDone()|| !inf_news.isDone()) {}
                    loggerThread.info("All thread finish. Read sending info.");
                    String infoAboutBiblio = "";
                    String listBook = "";
                    String listNews = "";
                    try {
                        infoAboutBiblio = inf_biblio.get();
                        listBook = inf_book.get();
                        listNews = inf_news.get();
                    } catch (Exception e) {
                        loggerThread.info("Exeption - информация потеряна!");
                        e.printStackTrace();
                    }
        ///////////////////////////////Формируем JSON для отправки клиенту//////////////////////////////////////////////
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
        loggerDAO.info("------------------------------------------------------------------------------------------------");
        loggerThread.info("------------------------------------------------------------------------------------------------");
    }
}
