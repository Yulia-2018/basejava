package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.util.*;

import static java.time.Month.*;


public class MainResume {
    public static void main(String[] args) {
        String fullName = "Григорий Кислин";
        Resume resume = new Resume(fullName);

        Map<ContactType, String> listContact = new EnumMap<>(ContactType.class);
        listContact.put(ContactType.PHONE, "+7(921) 855-0482");
        listContact.put(ContactType.SKYPE, "grigory.kislin");
        listContact.put(ContactType.EMAIL, "gkislin@yandex.ru");
        listContact.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        listContact.put(ContactType.GITHUB, "https://github.com/gkislin");
        listContact.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
        listContact.put(ContactType.HOME_PAGE, "http://gkislin.ru/");
        resume.setContacts(listContact);

        List<Organization> listExperience = new ArrayList<>();
        Organization experience1 = new Organization(
                "Java Online Projects",
                "https://www.Java.com",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2010, JANUARY), DateUtil.of(2013, OCTOBER), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."),
                        new BodyOrganization(DateUtil.of(2014, NOVEMBER), DateUtil.of(2018, AUGUST), "Руководитель группы.", "Создание программного комплекса.")));
        listExperience.add(experience1);
        Organization experience2 = new Organization(
                "Wrike",
                "https://www.Wrike.com",
                Arrays.asList(new BodyOrganization(DateUtil.of(1998, JANUARY), DateUtil.of(1999, OCTOBER), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis).")));
        listExperience.add(experience2);

        List<Organization> listEducation = new ArrayList<>();
        Organization education1 = new Organization(
                "Coursera",
                "https://www.Coursera.com",
                Arrays.asList(new BodyOrganization(DateUtil.of(2000, MAY), DateUtil.of(2000, OCTOBER), "\"Functional Programming Principles in Scala\" by Martin Odersky", null)));
        listEducation.add(education1);
        Organization education2 = new Organization(
                "Luxoft",
                "https://www.Luxoft.com",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2000, MARCH), DateUtil.of(2000, OCTOBER), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null),
                        new BodyOrganization(DateUtil.of(2005, MAY), DateUtil.of(2005, SEPTEMBER), "Курсы повышения квалификации", null)));
        listEducation.add(education2);

        Map<SectionType, Section> listSection = new EnumMap<>(SectionType.class);
        listSection.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        listSection.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        listSection.put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("С 2013 года: разработка проектов \"Разработка Web приложения\"", "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike")));
        listSection.put(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2", "Version control: Subversion, Git, Mercury, ClearCase, Perforce")));
        listSection.put(SectionType.EXPERIENCE, new OrganizationSection(listExperience));
        listSection.put(SectionType.EDUCATION, new OrganizationSection(listEducation));

        resume.setSections(listSection);

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
