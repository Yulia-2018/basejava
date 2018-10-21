package com.urise.webapp;

import com.urise.webapp.model.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;


public class MainResume {
    public static void main(String[] args) throws ParseException {
        String uuid = "1";
        String fullName = "Григорий Кислин";

        Map<ContactsType, String> listContacts = new EnumMap<ContactsType, String>(ContactsType.class);
        listContacts.put(ContactsType.PHONE, "+7(921) 855-0482");
        listContacts.put(ContactsType.SKYPE, "grigory.kislin");
        listContacts.put(ContactsType.EMAIL, "gkislin@yandex.ru");
        listContacts.put(ContactsType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        listContacts.put(ContactsType.GITHUB, "https://github.com/gkislin");
        listContacts.put(ContactsType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
        listContacts.put(ContactsType.HOMEPAGE, "http://gkislin.ru/");

        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        ListSection achievement = new ListSection(Arrays.asList("С 2013 года: разработка проектов \"Разработка Web приложения\"", "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike"));
        ListSection qualifications = new ListSection(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2", "Version control: Subversion, Git, Mercury, ClearCase, Perforce"));

        LocalDate date1 = LocalDate.of(2018, 5, 25);
        LocalDate date2 = LocalDate.now();

        Map<String, OrganizationSection> listExperience = new HashMap<>();
        OrganizationSection experience1 = new OrganizationSection(date1, date2, "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        listExperience.put("Java Online Projects", experience1);
        OrganizationSection experience2 = new OrganizationSection(date1, date2, "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis).");
        listExperience.put("Wrike", experience2);

        Map<String, OrganizationSection> listEducation = new HashMap<>();
        OrganizationSection education1 = new OrganizationSection(date1, date2, "", "\"Functional Programming Principles in Scala\" by Martin Odersky");
        listEducation.put("Coursera", education1);
        OrganizationSection education2 = new OrganizationSection(date1, date2, "", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        listEducation.put("Luxoft", education2);

        Resume resume = new Resume(
                uuid,
                fullName,
                listContacts,
                objective,
                personal,
                achievement,
                qualifications,
                listExperience,
                listEducation);

        System.out.println(resume.getFullName() + "\n");

        System.out.println("Контакты");
        for (Map.Entry<ContactsType, String> contact : listContacts.entrySet()) {
            System.out.println(contact.getKey().getTitle() + ": " + contact.getValue());
        }
        System.out.println("");

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
            switch (type) {
                case OBJECTIVE:
                    System.out.println(resume.getObjective() + "\n");
                    break;
                case PERSONAL:
                    System.out.println(resume.getPersonal() + "\n");
                    break;
                case ACHIEVEMENT:
                    System.out.println(resume.getAchievement());
                    break;
                case QUALIFICATIONS:
                    System.out.println(resume.getQualifications());
                    break;
                case EXPERIENCE:
                    //System.out.println(resume.getExperience());
                    for (Map.Entry<String, OrganizationSection> experience : listExperience.entrySet()) {
                        System.out.println(experience.getKey() + "\n" + experience.getValue() + "\n");
                    }
                    break;
                case EDUCATION:
                    //System.out.println(resume.getEducation());
                    for (Map.Entry<String, OrganizationSection> education : listEducation.entrySet()) {
                        System.out.println(education.getKey() + "\n" + education.getValue() + "\n");
                    }
                    break;
            }
        }
    }
}
