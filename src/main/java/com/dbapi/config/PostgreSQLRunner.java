package com.dbapi.config;

import com.dbapi.model.Student;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class PostgreSQLRunner implements ApplicationRunner {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try (Connection connection = dataSource.getConnection()){
            System.out.println(dataSource.getClass());
            System.out.println(connection.getMetaData().getURL());
            System.out.println(connection.getMetaData().getUserName());

            Statement statement = connection.createStatement();
            String sql = "DROP TABLE IF EXISTS students; CREATE TABLE students (name varchar(100), email varchar(100), degree varchar(100), graduation integer, PRIMARY KEY (email));";
            statement.executeUpdate(sql);
        }
        JdbcTemplate template = this.jdbcTemplate;

        Document doc = Jsoup.connect("link").get();

        Elements degree = doc.select("div.element");
        Elements stds = doc.select("ul.element");

        for (int i = 0; i < degree.size(); i++) {
            String now = degree.get(i).text().split(" ")[0].toLowerCase();
            for (String std : stds.get(i).select("p.CDt4Ke.zfr3Q").eachText()) {
                String[] arr = std.split(",");
                Student student = new Student(arr[0].trim(), arr[1].trim(), now, Integer.parseInt(arr[2].trim()));
                if (student.getDegree().equals("undergraduate")){
                    student.setDegree("undergrad");
                }

                String sql = "INSERT INTO students (name, email, degree, graduation) VALUES (?, ?, ?, ?)";
                template.update(sql, student.getName(), student.getEmail(), student.getDegree(), student.getGraduation());

            }
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}