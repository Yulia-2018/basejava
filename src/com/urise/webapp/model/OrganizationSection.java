package com.urise.webapp.model;

import java.util.List;

public class OrganizationSection extends Section {

    private List<Organization> list;

    public OrganizationSection(List<Organization> list) {
        this.list = list;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Organization element : list) {
            sb.append(element).append("\n");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return list != null ? list.equals(that.list) : that.list == null;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }
}
