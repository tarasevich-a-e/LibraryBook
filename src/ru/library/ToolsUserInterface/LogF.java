package ru.library.ToolsUserInterface;

import java.io.*;

public class LogF {
    private static String filename = "log_prog.txt";
    private static PrintWriter printWriter;

    public LogF() {
    }

    private static void createLog() {
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + "\\" + filename, true)));
        } catch (FileNotFoundException var2) {
            var2.printStackTrace();
        }
    }

    public static void writeLog(String str) {
        createLog();
        printWriter.println(str);
        printWriter.flush();
        close();
    }

    private static void close() {
        printWriter.close();
    }
}
