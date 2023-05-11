package com.dbapi.model;

public class Student {
    private String name;
    private String email;
    private String degree;
    private int graduation;

    public Student (){};

    public Student(String name, String email, String degree, int graduation) {
        this.name = name;
        this.email = email;
        this.degree = degree;
        this.graduation = graduation;
    }


    @Override
    public String toString() {
        return "Student{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", degree='" + degree + '\'' +
                ", graduation=" + graduation +
                '}';
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setGraduation(int graduation) {
        this.graduation = graduation;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDegree() {
        return degree;
    }

    public int getGraduation() {
        return graduation;
    }
}
