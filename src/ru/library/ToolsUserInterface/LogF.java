package ru.library.ToolsUserInterface;

import java.io.*;

public class LogF {
    private String filename = "log_prog.txt";
    private PrintWriter printWriter;

    public LogF() {
    }

    public void createLog() {
        try {
            this.printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + "\\" + this.filename, true)));
        } catch (FileNotFoundException var2) {
            var2.printStackTrace();
        }
    }

    public void writeLog(String str) {
        this.printWriter.println(str);
        this.printWriter.flush();
    }

    public void close() {
        this.printWriter.close();
    }
}
