package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.util.*;

import static java.time.Month.*;

public class ResumeTestData {
    public static List<Resume> createResume() {

        List<Resume> listResume = new ArrayList<>();

        // resume_1
        final Resume resume_1 = new Resume("uuid1", "Petrov Maxim");

        resume_1.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume_1.addContact(ContactType.SKYPE, "Max.Petrov");
        resume_1.addContact(ContactType.EMAIL, "MaxPetrov@yandex.ru");
        resume_1.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/MaxPetrov");
        resume_1.addContact(ContactType.GITHUB, "https://github.com/MaxPetrov");
        resume_1.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/MaxPetrov");
        resume_1.addContact(ContactType.HOME_PAGE, "http://MaxPetrov.ru/");

        List<Organization> listExperience_1 = new ArrayList<>();
        listExperience_1.add(new Organization(
                "Java Online Projects",
                "https://www.Java.com",
                new Organization.Position(DateUtil.of(2015, AUGUST), DateUtil.of(2016, FEBRUARY), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."),
                new Organization.Position(DateUtil.of(2014, NOVEMBER), DateUtil.of(2015, AUGUST), "Руководитель группы.", "Создание программного комплекса.")));
        listExperience_1.add(new Organization(
                "Wrike",
                "https://www.Wrike.com",
                new Organization.Position(DateUtil.of(2017, OCTOBER), DateUtil.of(2018, APRIL), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis).")));

        List<Organization> listEducation_1 = new ArrayList<>();
        listEducation_1.add(new Organization(
                "Coursera",
                "https://www.Coursera.com",
                new Organization.Position(DateUtil.of(2011, OCTOBER), DateUtil.of(2012, DECEMBER), "\"Functional Programming Principles in Scala\" by Martin Odersky", null)));
        listEducation_1.add(new Organization(
                "Luxoft",
                "https://www.Luxoft.com",
                new Organization.Position(DateUtil.of(2013, NOVEMBER), DateUtil.of(2014, OCTOBER), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null)));

        resume_1.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume_1.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume_1.addSection(SectionType.ACHIEVEMENT, new ListSection("С 2013 года: разработка проектов \"Разработка Web приложения\"", "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike"));
        resume_1.addSection(SectionType.QUALIFICATIONS, new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2", "Version control: Subversion, Git, Mercury, ClearCase, Perforce"));
        resume_1.addSection(SectionType.EXPERIENCE, new OrganizationSection(listExperience_1));
        resume_1.addSection(SectionType.EDUCATION, new OrganizationSection(listEducation_1));

        // resume_2
        final Resume resume_2 = new Resume("uuid2", "Inanov Dmitrii");

        resume_2.addContact(ContactType.PHONE, "+7(921) 840-0472");
        resume_2.addContact(ContactType.SKYPE, "Dmitrii.Inanov");
        resume_2.addContact(ContactType.EMAIL, "DInanov@yandex.ru");
        resume_2.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/DInanov");
        resume_2.addContact(ContactType.GITHUB, "https://github.com/DInanov");
        resume_2.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/DInanov");
        resume_2.addContact(ContactType.HOME_PAGE, "http://DInanov.ru/");

        List<Organization> listExperience_2 = new ArrayList<>();
        listExperience_2.add(new Organization(
                "RIT Center",
                null,
                new Organization.Position(DateUtil.of(2012, MARCH), DateUtil.of(2014, OCTOBER), "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO.")));
        listExperience_2.add(new Organization(
                "Luxoft (Deutsche Bank)",
                "https://www.luxoft.ru/",
                new Organization.Position(DateUtil.of(2010, MAY), DateUtil.of(2012, JUNE), "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle).")));

        List<Organization> listEducation_2 = new ArrayList<>();
        listEducation_2.add(new Organization(
                "Siemens AG",
                "https://www.siemens.com/ru/ru/home.html",
                new Organization.Position(DateUtil.of(2005, AUGUST), DateUtil.of(2005, OCTOBER), "3 месяца обучения мобильным IN сетям (Берлин)", null)));
        listEducation_2.add(new Organization(
                "Alcatel",
                "http://www.alcatel.ru/",
                new Organization.Position(DateUtil.of(1997, OCTOBER), DateUtil.of(1998, SEPTEMBER), "6 месяцев обучения цифровым телефонным сетям (Москва)", null)));

        resume_2.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок"));
        resume_2.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума"));
        resume_2.addSection(SectionType.ACHIEVEMENT, new ListSection("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM"));
        resume_2.addSection(SectionType.QUALIFICATIONS, new ListSection("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle", "MySQL, SQLite, MS SQL, HSQLDB"));
        resume_2.addSection(SectionType.EXPERIENCE, new OrganizationSection(listExperience_2));
        resume_2.addSection(SectionType.EDUCATION, new OrganizationSection(listEducation_2));

        // resume_3
        final Resume resume_3 = new Resume("uuid3", "Petrov Maxim");

        resume_2.addContact(ContactType.PHONE, "+7(921) 875-0262");
        resume_2.addContact(ContactType.SKYPE, "Maxim.Petrov");
        resume_3.addContact(ContactType.EMAIL, "MPetrov@yandex.ru");
        resume_3.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/MPetrov");
        resume_3.addContact(ContactType.GITHUB, "https://github.com/MPetrov");
        resume_3.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/MPetrov");
        resume_3.addContact(ContactType.HOME_PAGE, "http://MPetrov.ru/");

        List<Organization> listExperience_3 = new ArrayList<>();
        listExperience_3.add(new Organization(
                "Yota",
                "https://www.yota.ru/",
                new Organization.Position(DateUtil.of(2008, JANUARY), DateUtil.of(2010, NOVEMBER), "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\"")));
        listExperience_3.add(new Organization(
                "Enkata",
                "http://enkata.com/",
                new Organization.Position(DateUtil.of(2007, SEPTEMBER), DateUtil.of(2008, OCTOBER), "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).")));

        List<Organization> listEducation_3 = new ArrayList<>();
        listEducation_3.add(new Organization(
                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "http://www.ifmo.ru/ru/",
                new Organization.Position(DateUtil.of(1993, DECEMBER), DateUtil.of(1996, JUNE), "Аспирантура (программист С, С++)", null)));

        resume_3.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий корпоративного обучения"));
        resume_3.addSection(SectionType.PERSONAL, new TextSection("Сильная логика"));
        resume_3.addSection(SectionType.ACHIEVEMENT, new ListSection("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA", "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов"));
        resume_3.addSection(SectionType.QUALIFICATIONS, new ListSection("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy", "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts", "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot)"));
        resume_3.addSection(SectionType.EXPERIENCE, new OrganizationSection(listExperience_3));
        resume_3.addSection(SectionType.EDUCATION, new OrganizationSection(listEducation_3));

        // resume_4
        final Resume resume_4 = new Resume("uuid4", "Inanov Petr");

        resume_4.addContact(ContactType.PHONE, "+7(921) 875-0488");
        resume_4.addContact(ContactType.SKYPE, "Petr.Inanov");
        resume_4.addContact(ContactType.EMAIL, "PInanov@yandex.ru");
        resume_4.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/PInanov");
        resume_4.addContact(ContactType.GITHUB, "https://github.com/PInanov");
        resume_4.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/PInanov");
        resume_4.addContact(ContactType.HOME_PAGE, "http://PInanov.ru/");

        List<Organization> listExperience_4 = new ArrayList<>();
        listExperience_4.add(new Organization(
                "Siemens AG",
                "https://www.siemens.com/ru/ru/home.html",
                new Organization.Position(DateUtil.of(2005, OCTOBER), DateUtil.of(2007, JUNE), "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)")));
        listExperience_4.add(new Organization(
                "Alcatel",
                "http://www.alcatel.ru/",
                new Organization.Position(DateUtil.of(1997, DECEMBER), DateUtil.of(2005, OCTOBER), "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)")));

        List<Organization> listEducation_4 = new ArrayList<>();
        listEducation_4.add(new Organization(
                "Заочная физико-техническая школа при МФТИ",
                "http://www.school.mipt.ru/",
                new Organization.Position(DateUtil.of(1984, NOVEMBER), DateUtil.of(1987, OCTOBER), "Закончил с отличием", null)));
        listEducation_4.add(new Organization(
                "КемГУ",
                null,
                new Organization.Position(DateUtil.of(2003, OCTOBER), DateUtil.of(2008, DECEMBER), "Факультет информатики", null)));

        resume_4.addSection(SectionType.OBJECTIVE, new TextSection("Разработчик"));
        resume_4.addSection(SectionType.PERSONAL, new TextSection("Креативность, инициативность. Пурист кода и архитектуры."));
        resume_4.addSection(SectionType.ACHIEVEMENT, new ListSection("Реализация протоколов по приему платежей всех основных платежных системы России", "Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django)", "Организация онлайн стажировок и ведение проектов"));
        resume_4.addSection(SectionType.QUALIFICATIONS, new ListSection("Python: Django", "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js", "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka"));
        resume_4.addSection(SectionType.EXPERIENCE, new OrganizationSection(listExperience_4));
        resume_4.addSection(SectionType.EDUCATION, new OrganizationSection(listEducation_4));

        // resume_5, For method "update"
        final Resume resume_5 = new Resume("uuid1", "Sidorov Konstantin");

        resume_5.addContact(ContactType.PHONE, "+7(921) 825-0432");
        resume_5.addContact(ContactType.SKYPE, "Konstantin.Sidorov");
        resume_5.addContact(ContactType.EMAIL, "KSidorov@yandex.ru");
        resume_5.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/KSidorov");
        resume_5.addContact(ContactType.GITHUB, "https://github.com/KSidorov");
        resume_5.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/KSidorov");
        resume_5.addContact(ContactType.HOME_PAGE, "http://KSidorov.ru/");

        List<Organization> listExperience_5 = new ArrayList<>();
        listExperience_5.add(new Organization(
                "Yota",
                null,
                new Organization.Position(DateUtil.of(1998, OCTOBER), DateUtil.of(2000, JUNE), "Программист", "Создание системы логирования")));
        listExperience_5.add(new Organization(
                "Wrike",
                "https://www.Wrike.com",
                new Organization.Position(DateUtil.of(2010, MARCH), DateUtil.of(2018, OCTOBER), "Старший разработчик", "Архитектурные проекты")));
        listExperience_5.add(new Organization(
                "ООО Плаза",
                null,
                new Organization.Position(DateUtil.of(2018, OCTOBER), DateUtil.of(2018, MAY), "Администратор", "Архитектурные проекты")));

        List<Organization> listEducation_5 = new ArrayList<>();
        listEducation_5.add(new Organization(
                "Coursera",
                "https://www.Coursera.com",
                new Organization.Position(DateUtil.of(2000, AUGUST), DateUtil.of(2000, OCTOBER), "Повышение квалификации", null)));
        listEducation_5.add(new Organization(
                "Luxoft",
                null,
                new Organization.Position(DateUtil.of(2010, JUNE), DateUtil.of(2010, OCTOBER), "Курс по оптимизации", null),
                new Organization.Position(DateUtil.of(2005, MAY), DateUtil.of(2005, SEPTEMBER), "Курсы повышения квалификации", null)));

        resume_5.addSection(SectionType.OBJECTIVE, new TextSection("Экономист"));
        resume_5.addSection(SectionType.PERSONAL, new TextSection("Инициативность."));
        resume_5.addSection(SectionType.ACHIEVEMENT, new ListSection("Удаленное взаимодействие (JMS/AKKA)", "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk", "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера"));
        resume_5.addSection(SectionType.QUALIFICATIONS, new ListSection("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS"));
        resume_5.addSection(SectionType.EXPERIENCE, new OrganizationSection(listExperience_5));
        resume_5.addSection(SectionType.EDUCATION, new OrganizationSection(listEducation_5));

        listResume.add(resume_1);
        listResume.add(resume_2);
        listResume.add(resume_3);
        listResume.add(resume_4);
        listResume.add(resume_5);

        return listResume;
    }
}
