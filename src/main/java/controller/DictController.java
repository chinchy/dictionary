package controller;

import utils.XmlConfig;

import java.sql.*;
import java.util.Vector;

public class DictController {
    Connection conn;
    Statement statement;

    public DictController() {
        String url = String.format("jdbc:postgresql://%s:5432/%s",
        new XmlConfig().getByKey("db_host"),
        new XmlConfig().getByKey("db_name"));

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url,
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
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT word FROM dict WHERE word ILIKE ?;");
            preparedStatement.setString(1, "%" + partOfWord + "%");
            ResultSet result = preparedStatement.executeQuery();
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
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT mean FROM dict WHERE word ILIKE ?");
            preparedStatement.setString(1, "%" + word + "%");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()){
                mean = result.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mean;
    }

    public String addWord(String word, String mean) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO dict(word, mean) VALUES (?, ?) RETURNING id");
            preparedStatement.setString(1, word);
            preparedStatement.setString(2, mean);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            return String.valueOf(result.getInt(1));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
