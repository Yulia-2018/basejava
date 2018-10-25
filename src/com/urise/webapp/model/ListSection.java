package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {

    private final List<String> items;

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String element : items) {
            sb.append(element).append("\n");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
