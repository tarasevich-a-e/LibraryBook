package ru.library.UserInterface;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.library.ToolsUserInterface.LogF;

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
    static LogF logF;

    public static JsonObject getParametersOfTheReguest(BufferedReader breader) {
        //Читаем тело POST
        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            while ((line = breader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            logF.writeLog("> CommonMetodForUI: Ошибка при чтении POST-запроса");
        }
        //Извлекаем данные из JSON
        JsonParser jsonParser = new JsonParser();
        Object obj = jsonParser.parse(sb.toString());
        JsonObject jsonObject = (JsonObject) obj;
        logF.writeLog("> CommonMetodForUI: В сервлет пришли следующие параметры: ");
        logF.writeLog("> CommonMetodForUI: jsonObject: " + jsonObject);
        //logF.writeLog("Парамметр log найден и равен " + jsonObject.get("log").toString());
        //if(jsonObject.has("priiivet")) {logF.writeLog("Парамметр priiivet найден и равен " + jsonObject.get("priiivet").toString());}
        return jsonObject;
    }

    public static JsonObject getParametersOfTheReguestGET(Map<String, String[]> Params) {
        //Читаем GET запрос
        HashMap<String,String> stringHashMap = new HashMap<String, String>();
        String str = "";
        for (Map.Entry<String, String []> entry:
            Params.entrySet()) {
            stringHashMap.put(entry.getKey(), entry.getValue()[0]);
            str +=  "\"" + entry.getKey() + "\":" + entry.getValue()[0] + ",";
        }
        str = str.substring(0, str.length() - 1);
        str = "{" + str + "}";
        //String str = stringHashMap.toString();
        //str = str.replace('=',':');
        //str = str.replaceAll(" ","");
        JsonParser jsonParser = new JsonParser();
        Object obj = jsonParser.parse(str);
        JsonObject jsonObject = (JsonObject) obj;

        return jsonObject;
    }
}
