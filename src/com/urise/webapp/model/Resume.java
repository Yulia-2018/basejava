package com.urise.webapp.model;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;

    private final String fullName;

    private Map<ContactsType, String> contacts;

    private TextSection objective;
    private TextSection personal;
    private ListSection achievement;
    private ListSection qualifications;
    private Map<String, OrganizationSection> experience;
    private Map<String, OrganizationSection> education;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume(String uuid,
                  String fullName,
                  Map<ContactsType, String> listContacts,
                  TextSection textObjective,
                  TextSection textPersonal,
                  ListSection listAchievement,
                  ListSection listQualifications,
                  Map<String, OrganizationSection> listExperience,
                  Map<String, OrganizationSection> listEducation) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        this.contacts = listContacts;
        this.objective = textObjective;
        this.personal = textPersonal;
        this.achievement = listAchievement;
        this.qualifications = listQualifications;
        this.experience = listExperience;
        this.education = listEducation;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactsType, String> getContacts() {
        return contacts;
    }

    public TextSection getObjective() {
        return objective;
    }

    public TextSection getPersonal() {
        return personal;
    }

    public ListSection getAchievement() {
        return achievement;
    }

    public ListSection getQualifications() {
        return qualifications;
    }

    public Map<String, OrganizationSection> getExperience() {
        return experience;
    }

    public Map<String, OrganizationSection> getEducation() {
        return education;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + ' ' + fullName;
    }
}
