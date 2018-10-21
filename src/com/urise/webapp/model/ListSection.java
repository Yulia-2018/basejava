package com.urise.webapp.model;

import java.util.List;

public class ListSection {

    private List<String> list;

    public ListSection(List<String> list) {
        this.list = list;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String element : list) {
            sb.append(element).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return list != null ? list.equals(that.list) : that.list == null;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }
}
