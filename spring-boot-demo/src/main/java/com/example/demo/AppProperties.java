package com.example.bajaj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
private String name;
private String regNo;
private String email;
private String finalSqlPath;


public String getName() { return name; }
public void setName(String name) { this.name = name; }


public String getRegNo() { return regNo; }
public void setRegNo(String regNo) { this.regNo = regNo; }


public String getEmail() { return email; }
public void setEmail(String email) { this.email = email; }


public String getFinalSqlPath() { return finalSqlPath; }
public void setFinalSqlPath(String finalSqlPath) { this.finalSqlPath = finalSqlPath; }
}
