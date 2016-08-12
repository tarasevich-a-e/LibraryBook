package ru.library.UserInterface;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by atarasevich on 22.07.16.
 */
public class CommonMetodForUI {
    final static Logger logger = Logger.getLogger(CommonMetodForUI.class);

    public static JsonObject getParametersOfTheReguest(BufferedReader breader) {
        //Читаем тело POST
        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            logger.info("Читаем буффер request'a");
            while ((line = breader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            logger.error(">>>>> Ошибка при чтении буффера request'a");
        }
        logger.info("Успех");
        //Извлекаем данные из JSON
        JsonParser jsonParser;
        JsonObject jsonObject = null;
        try {
            logger.info("Конвертируем буффер request'a");
            jsonParser = new JsonParser();
            Object obj = jsonParser.parse(sb.toString());
            jsonObject = (JsonObject) obj;
        } catch (Exception e) {
            logger.error(">>>>> Ошибка при конвертации буффера request'a");
        }
        logger.info("Успех");
        return jsonObject;
    }

    public static JsonObject getParametersOfTheReguestGET(Map<String, String[]> Params) {
        //Читаем GET запрос
        HashMap<String,String> stringHashMap = new HashMap<String, String>();
        String str = "";
        logger.info("Читаем map request'a");
        try {
            for (Map.Entry<String, String []> entry:
                Params.entrySet()) {
                stringHashMap.put(entry.getKey(), entry.getValue()[0]);
                str +=  "\"" + entry.getKey() + "\":" + entry.getValue()[0] + ",";
            }
        } catch (Exception e) {
            logger.error(">>>>> Ошибка при чтении map request'a");
        }
        logger.info("Успех");
        str = str.substring(0, str.length() - 1);
        str = "{" + str + "}";
        JsonParser jsonParser;
        JsonObject jsonObject = null;
        try {
            logger.info("Конвертируем map request'a");
            jsonParser = new JsonParser();
            Object obj = jsonParser.parse(str);
            jsonObject = (JsonObject) obj;
        } catch (Exception e) {
            logger.error(">>>>> Ошибка при конвертации map request'a");
        }
        logger.info("Успех");
        return jsonObject;
    }
}
