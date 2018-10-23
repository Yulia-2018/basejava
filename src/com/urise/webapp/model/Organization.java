package com.urise.webapp.model;

import java.time.LocalDate;

public class Organization {

    private String name;
    private String url;
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private String description;

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String position, String description) {
        this.name = name;
        this.url = url;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return url + "\n" + startDate.toString() + " - " + endDate.toString() + "\n" + position + "\n" + description;
    }

}
