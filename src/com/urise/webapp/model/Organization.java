package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {

    private final Link homePage;
    private final List<BodyOrganization> bodyOrganization;

    public Organization(String name, String url, List<BodyOrganization> bodyOrganization) {
        Objects.requireNonNull(bodyOrganization, "bodyOrganization must not be null");
        this.homePage = new Link(name, url);
        this.bodyOrganization = bodyOrganization;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(homePage.getName()).append(" ").append(homePage.getUrl());
        for (BodyOrganization element : bodyOrganization) {
            sb.append("\n").append(element);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return bodyOrganization.equals(that.bodyOrganization);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + bodyOrganization.hashCode();
        return result;
    }
}
