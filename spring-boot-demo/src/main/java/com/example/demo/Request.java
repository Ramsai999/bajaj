package com.example.bajaj.dto;

public class Request {
    private String name;
    private String regNo;
    private String email;

    public Request(String name, String regNo, String email) {
        this.name = name;
        this.regNo = regNo;
        this.email = email;
    }

    // getters (needed for JSON serialization)
    public String getName() { return name; }
    public String getRegNo() { return regNo; }
    public String getEmail() { return email; }
}
