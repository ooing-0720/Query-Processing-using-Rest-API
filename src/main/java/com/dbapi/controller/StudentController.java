package com.dbapi.controller;

import com.dbapi.model.Student;
import com.dbapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) throws Exception {
        System.out.println("controller");
        this.service = service;
    }

    @GetMapping("/students/degree")
    public String findDegreeByName(@RequestParam String name) {
        List<Student> student = service.findByName(name);

        if (student.size() == 0) {
            return "No such student";
        }
        else if (student.size() == 1){
            return student.get(0).getName() + ":" + student.get(0).getDegree();
        }
        else{
            return  "There are multiple students with the same name. Please provide an email address instead.";
        }
    }

    @GetMapping("/students/email")
    public String findEmailByName(@RequestParam String name) {
        List<Student> student = service.findByName(name);

        if (student.size() == 0) {
            return "No such student";
        }
        else if (student.size() == 1){
            return student.get(0).getName() + ":" + student.get(0).getEmail();
        }
        else{
            return  "There are multiple students with the same name. Please contact the administrator by phone.";
        }
    }

    @GetMapping("/students/stat")
    public String countByDegreee(@RequestParam String degree) {
        int cnt = service.countByDegree(degree);
        return "Number of " + degree + "'s student : " + cnt;
    }

    @PutMapping("/students/register")
    public String createStudent(@RequestBody Student student) throws Exception {
        if (service.findByName(student.getName()).size() != 0) {
            return "Already registered";
        } else {
            service.insert(student);
            return "Registration successful";
        }
    }
}
