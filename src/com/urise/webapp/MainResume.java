package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;


public class MainResume {
    public static void main(String[] args) {
        String fullName = "Григорий Кислин";
        Resume resume = new Resume(fullName);

        Map<ContactType, String> listContact = new EnumMap<ContactType, String>(ContactType.class);
        listContact.put(ContactType.PHONE, "+7(921) 855-0482");
        listContact.put(ContactType.SKYPE, "grigory.kislin");
        listContact.put(ContactType.EMAIL, "gkislin@yandex.ru");
        listContact.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        listContact.put(ContactType.GITHUB, "https://github.com/gkislin");
        listContact.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
        listContact.put(ContactType.HOMEPAGE, "http://gkislin.ru/");
        resume.setContact(listContact);

        LocalDate date1 = LocalDate.of(2018, 5, 25);
        LocalDate date2 = LocalDate.now();

        Map<String, Organization> listExperience = new HashMap<>();
        Organization experience1 = new Organization("Java Online Projects", "https://www.Java.com", date1, date2, "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        listExperience.put(experience1.getName(), experience1);
        Organization experience2 = new Organization("Wrike", "https://www.Wrike.com", date1, date2, "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis).");
        listExperience.put(experience2.getName(), experience2);

        Map<String, Organization> listEducation = new HashMap<>();
        Organization education1 = new Organization("Coursera", "https://www.Coursera.com", date1, date2, "", "\"Functional Programming Principles in Scala\" by Martin Odersky");
        listEducation.put(education1.getName(), education1);
        Organization education2 = new Organization("Luxoft", "https://www.Luxoft.com", date1, date2, "", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        listEducation.put(education2.getName(), education2);

        Map<SectionType, Section> listSection = new EnumMap<SectionType, Section>(SectionType.class);
        listSection.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        listSection.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        listSection.put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("С 2013 года: разработка проектов \"Разработка Web приложения\"", "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike")));
        listSection.put(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2", "Version control: Subversion, Git, Mercury, ClearCase, Perforce")));
        listSection.put(SectionType.EXPERIENCE, new OrganizationSection(listExperience));
        listSection.put(SectionType.EDUCATION, new OrganizationSection(listEducation));

        resume.setSection(listSection);

        System.out.println(resume.getFullName() + "\n");

        System.out.println("Контакты");
        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle() + ": " + resume.getContact(type));
        }
        System.out.println("");

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle() + "\n" + resume.getSection(type) + "\n");
        }
    }
}
