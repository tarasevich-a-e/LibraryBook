package ru.library;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import ru.library.Book;

public class ZaprosBD {
    Statement statement;

    public ZaprosBD() {
    }

    public String getJSON(String zapros, Connection connection) {
        ArrayList books = new ArrayList();

        try {
            this.statement = connection.createStatement();
            ResultSet gson = this.statement.executeQuery(zapros);

            while(gson.next()) {
                books.add(new Book(gson.getInt("id_b"), gson.getString("author_b"), gson.getString("name_b"), gson.getInt("release_b"), gson.getString("type_b")));
            }

            gson.close();
            this.statement.close();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

        Gson gson1 = new Gson();
        return gson1.toJson(books);
    }
}
