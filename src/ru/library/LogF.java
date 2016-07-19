package ru.library;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LogF {
    private String filename = "log_prog.txt";
    private PrintWriter printWriter;

    public LogF() {
    }

    public void createLog() {
        try {
            this.printWriter = new PrintWriter(System.getProperty("user.dir") + "\\" + this.filename);
        } catch (FileNotFoundException var2) {
            var2.printStackTrace();
        }

    }

    public void writeLog(String str) {
        this.printWriter.write(str + "\n");
        this.printWriter.flush();
    }

    public void close() {
        this.printWriter.close();
    }
}
