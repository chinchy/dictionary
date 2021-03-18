package controller;

import utils.XmlConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DictController {
    Statement statement;

    public DictController() {
        String url = String.format("jdbc:postgresql://%s:5432/%s",
        new XmlConfig().getByKey("db_host"),
        new XmlConfig().getByKey("db_name"));

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url,
                    new XmlConfig().getByKey("db_user"),
                    new XmlConfig().getByKey("db_pass"));
            statement = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Vector<String> getWordsList(String partOfWord) {
        Vector<String> words = new Vector<>();
        try {
            ResultSet result = statement.executeQuery("SELECT word FROM dict WHERE word ILIKE '%" + partOfWord + "%'");
            while (result.next()){
                words.add(result.getString(1));
            }
            if (words.isEmpty())
                words.add("СЛОВ НЕ НАЙДЕНО");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }

    public String getMean(String word) {
        String mean = "";
        try {
            ResultSet result = statement.executeQuery("SELECT mean FROM dict WHERE word ILIKE '" + word + "'");
            while (result.next()){
                mean = result.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mean;
    }

}
