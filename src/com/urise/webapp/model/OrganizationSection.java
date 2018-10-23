package com.urise.webapp.model;

import java.util.Map;

public class OrganizationSection extends Section {

    private Map<String, Organization> map;

    public OrganizationSection(Map<String, Organization> map) {
        this.map = map;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Organization> element : map.entrySet()) {
            sb.append("\n").append(element.getKey()).append("\n").append(element.getValue()).append("\n");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }
}
