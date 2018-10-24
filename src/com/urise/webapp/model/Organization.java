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

    public String toString() {
        if (position == null) {
            return "\n" + name + "\n" + url + "\n" + startDate.toString() + " - " + endDate.toString() + "\n" + description;
        } else
            return "\n" + name + "\n" + url + "\n" + startDate.toString() + " - " + endDate.toString() + "\n" + position + "\n" + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
