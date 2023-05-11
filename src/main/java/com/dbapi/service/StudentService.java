package com.dbapi.service;

import com.dbapi.config.PostgreSQLRunner;
import com.dbapi.model.Student;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;

import javax.sql.*;
import java.util.List;


@Service
public class StudentService {

    private DataSource dataSource;
    private JdbcTemplate template;
    private final PostgreSQLRunner runner;

    @Autowired
    public StudentService(PostgreSQLRunner runner) {
       System.out.println("service");
       this.runner = runner;
       this.dataSource = runner.getDataSource();
       this.template = runner.getJdbcTemplate();
    }

    public void insert(Student student) throws Exception {
        String sql = "INSERT INTO students VALUES (?, ?, ?, ?)";
        template.update(sql, student.getName(), student.getEmail(), student.getDegree(), student.getGraduation());
    }

    public List<Student> findByName(String name) {
        try {
            String sql = "SELECT * FROM students WHERE name = ?";
            return template.query(sql, (rs, sname) -> new Student(
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("degree"),
                    rs.getInt("graduation")
            ), name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int countByDegree(String degree) {
        String sql = "SELECT COUNT(*) FROM students GROUP BY degree HAVING degree='" + degree + "'";
        return template.queryForObject(sql, Integer.class);
    }
}
