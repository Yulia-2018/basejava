package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import static java.time.Month.*;


public class MainResume {
    public static void main(String[] args) {
        String fullName = "Григорий Кислин";
        Resume resume = new Resume(fullName);

        resume.setContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.setContact(ContactType.SKYPE, "grigory.kislin");
        resume.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.setContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
        resume.setContact(ContactType.HOME_PAGE, "http://gkislin.ru/");

        List<Organization> listExperience = new ArrayList<>();
        Organization experience1 = new Organization(
                "Java Online Projects",
                "https://www.Java.com",
                new Organization.Position(DateUtil.of(2010, JANUARY), DateUtil.of(2013, OCTOBER), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."),
                new Organization.Position(DateUtil.of(2014, NOVEMBER), DateUtil.of(2018, AUGUST), "Руководитель группы.", "Создание программного комплекса."));
        listExperience.add(experience1);
        Organization experience2 = new Organization(
                "Wrike",
                "https://www.Wrike.com",
                new Organization.Position(DateUtil.of(1998, JANUARY), DateUtil.of(1999, OCTOBER), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)."));
        listExperience.add(experience2);

        List<Organization> listEducation = new ArrayList<>();
        Organization education1 = new Organization(
                "Coursera",
                "https://www.Coursera.com",
                new Organization.Position(DateUtil.of(2000, MAY), DateUtil.of(2000, OCTOBER), "\"Functional Programming Principles in Scala\" by Martin Odersky", null));
        listEducation.add(education1);
        Organization education2 = new Organization(
                "Luxoft",
                "https://www.Luxoft.com",
                new Organization.Position(DateUtil.of(2000, MARCH), DateUtil.of(2000, OCTOBER), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null),
                new Organization.Position(DateUtil.of(2005, MAY), DateUtil.of(2005, SEPTEMBER), "Курсы повышения квалификации", null));
        listEducation.add(education2);

        resume.setSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.setSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection("С 2013 года: разработка проектов \"Разработка Web приложения\"", "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike"));
        resume.setSection(SectionType.QUALIFICATIONS, new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2", "Version control: Subversion, Git, Mercury, ClearCase, Perforce"));
        resume.setSection(SectionType.EXPERIENCE, new OrganizationSection(listExperience));
        resume.setSection(SectionType.EDUCATION, new OrganizationSection(listEducation));

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
