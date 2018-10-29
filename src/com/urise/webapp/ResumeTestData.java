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

        Map<ContactType, String> listContact_1 = new EnumMap<>(ContactType.class);
        listContact_1.put(ContactType.PHONE, "+7(921) 855-0482");
        listContact_1.put(ContactType.SKYPE, "Max.Petrov");
        listContact_1.put(ContactType.EMAIL, "MaxPetrov@yandex.ru");
        listContact_1.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/MaxPetrov");
        listContact_1.put(ContactType.GITHUB, "https://github.com/MaxPetrov");
        listContact_1.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/MaxPetrov");
        listContact_1.put(ContactType.HOME_PAGE, "http://MaxPetrov.ru/");
        resume_1.setContacts(listContact_1);

        List<Organization> listExperience_1 = new ArrayList<>();
        listExperience_1.add(new Organization(
                "Java Online Projects",
                "https://www.Java.com",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2015, AUGUST), DateUtil.of(2016, FEBRUARY), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."),
                        new BodyOrganization(DateUtil.of(2014, NOVEMBER), DateUtil.of(2015, AUGUST), "Руководитель группы.", "Создание программного комплекса."))));
        listExperience_1.add(new Organization(
                "Wrike",
                "https://www.Wrike.com",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2017, OCTOBER), DateUtil.of(2018, APRIL), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)."))));

        List<Organization> listEducation_1 = new ArrayList<>();
        listEducation_1.add(new Organization(
                "Coursera",
                "https://www.Coursera.com",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2011, OCTOBER), DateUtil.of(2012, DECEMBER), "\"Functional Programming Principles in Scala\" by Martin Odersky", null))));
        listEducation_1.add(new Organization(
                "Luxoft",
                "https://www.Luxoft.com",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2013, NOVEMBER), DateUtil.of(2014, OCTOBER), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null))));

        Map<SectionType, AbstractSection> listSection_1 = new EnumMap<>(SectionType.class);
        listSection_1.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        listSection_1.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        listSection_1.put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("С 2013 года: разработка проектов \"Разработка Web приложения\"", "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike")));
        listSection_1.put(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2", "Version control: Subversion, Git, Mercury, ClearCase, Perforce")));
        listSection_1.put(SectionType.EXPERIENCE, new OrganizationSection(listExperience_1));
        listSection_1.put(SectionType.EDUCATION, new OrganizationSection(listEducation_1));

        resume_1.setSections(listSection_1);

        // resume_2
        final Resume resume_2 = new Resume("uuid2", "Inanov Dmitrii");

        Map<ContactType, String> listContact_2 = new EnumMap<>(ContactType.class);
        listContact_2.put(ContactType.PHONE, "+7(921) 840-0472");
        listContact_2.put(ContactType.SKYPE, "Dmitrii.Inanov");
        listContact_2.put(ContactType.EMAIL, "DInanov@yandex.ru");
        listContact_2.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/DInanov");
        listContact_2.put(ContactType.GITHUB, "https://github.com/DInanov");
        listContact_2.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/DInanov");
        listContact_2.put(ContactType.HOME_PAGE, "http://DInanov.ru/");
        resume_2.setContacts(listContact_2);

        List<Organization> listExperience_2 = new ArrayList<>();
        listExperience_2.add(new Organization(
                "RIT Center",
                null,
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2012, MARCH), DateUtil.of(2014, OCTOBER), "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO."))));
        listExperience_2.add(new Organization(
                "Luxoft (Deutsche Bank)",
                "https://www.luxoft.ru/",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2010, MAY), DateUtil.of(2012, JUNE), "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle)."))));

        List<Organization> listEducation_2 = new ArrayList<>();
        listEducation_2.add(new Organization(
                "Siemens AG",
                "https://www.siemens.com/ru/ru/home.html",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2005, AUGUST), DateUtil.of(2005, OCTOBER), "3 месяца обучения мобильным IN сетям (Берлин)", null))));
        listEducation_2.add(new Organization(
                "Alcatel",
                "http://www.alcatel.ru/",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(1997, OCTOBER), DateUtil.of(1998, SEPTEMBER), "6 месяцев обучения цифровым телефонным сетям (Москва)", null))));

        Map<SectionType, AbstractSection> listSection_2 = new EnumMap<>(SectionType.class);
        listSection_2.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок"));
        listSection_2.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума"));
        listSection_2.put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM")));
        listSection_2.put(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle", "MySQL, SQLite, MS SQL, HSQLDB")));
        listSection_2.put(SectionType.EXPERIENCE, new OrganizationSection(listExperience_2));
        listSection_2.put(SectionType.EDUCATION, new OrganizationSection(listEducation_2));

        resume_2.setSections(listSection_2);

        // resume_3
        final Resume resume_3 = new Resume("uuid3", "Petrov Maxim");

        Map<ContactType, String> listContact_3 = new EnumMap<>(ContactType.class);
        listContact_3.put(ContactType.PHONE, "+7(921) 875-0262");
        listContact_3.put(ContactType.SKYPE, "Maxim.Petrov");
        listContact_3.put(ContactType.EMAIL, "MPetrov@yandex.ru");
        listContact_3.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/MPetrov");
        listContact_3.put(ContactType.GITHUB, "https://github.com/MPetrov");
        listContact_3.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/MPetrov");
        listContact_3.put(ContactType.HOME_PAGE, "http://MPetrov.ru/");
        resume_3.setContacts(listContact_3);

        List<Organization> listExperience_3 = new ArrayList<>();
        listExperience_3.add(new Organization(
                "Yota",
                "https://www.yota.ru/",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2008, JANUARY), DateUtil.of(2010, NOVEMBER), "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\""))));
        listExperience_3.add(new Organization(
                "Enkata",
                "http://enkata.com/",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2007, SEPTEMBER), DateUtil.of(2008, OCTOBER), "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."))));

        List<Organization> listEducation_3 = new ArrayList<>();
        listEducation_3.add(new Organization(
                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "http://www.ifmo.ru/ru/",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(1993, DECEMBER), DateUtil.of(1996, JUNE), "Аспирантура (программист С, С++)", null))));

        Map<SectionType, AbstractSection> listSection_3 = new EnumMap<>(SectionType.class);
        listSection_3.put(SectionType.OBJECTIVE, new TextSection("Ведущий корпоративного обучения"));
        listSection_3.put(SectionType.PERSONAL, new TextSection("Сильная логика"));
        listSection_3.put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA", "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов")));
        listSection_3.put(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy", "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts", "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot)")));
        listSection_3.put(SectionType.EXPERIENCE, new OrganizationSection(listExperience_3));
        listSection_3.put(SectionType.EDUCATION, new OrganizationSection(listEducation_3));

        resume_3.setSections(listSection_3);

        // resume_4
        final Resume resume_4 = new Resume("uuid4", "Inanov Petr");

        Map<ContactType, String> listContact_4 = new EnumMap<>(ContactType.class);
        listContact_4.put(ContactType.PHONE, "+7(921) 875-0488");
        listContact_4.put(ContactType.SKYPE, "Petr.Inanov");
        listContact_4.put(ContactType.EMAIL, "PInanov@yandex.ru");
        listContact_4.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/PInanov");
        listContact_4.put(ContactType.GITHUB, "https://github.com/PInanov");
        listContact_4.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/PInanov");
        listContact_4.put(ContactType.HOME_PAGE, "http://PInanov.ru/");
        resume_4.setContacts(listContact_4);

        List<Organization> listExperience_4 = new ArrayList<>();
        listExperience_4.add(new Organization(
                "Siemens AG",
                "https://www.siemens.com/ru/ru/home.html",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2005, OCTOBER), DateUtil.of(2007, JUNE), "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)"))));
        listExperience_4.add(new Organization(
                "Alcatel",
                "http://www.alcatel.ru/",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(1997, DECEMBER), DateUtil.of(2005, OCTOBER), "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)"))));

        List<Organization> listEducation_4 = new ArrayList<>();
        listEducation_4.add(new Organization(
                "Заочная физико-техническая школа при МФТИ",
                "http://www.school.mipt.ru/",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(1984, NOVEMBER), DateUtil.of(1987, OCTOBER), "Закончил с отличием", null))));
        listEducation_4.add(new Organization(
                "КемГУ",
                null,
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2003, OCTOBER), DateUtil.of(2008, DECEMBER), "Факультет информатики", null))));

        Map<SectionType, AbstractSection> listSection_4 = new EnumMap<>(SectionType.class);
        listSection_4.put(SectionType.OBJECTIVE, new TextSection("Разработчик"));
        listSection_4.put(SectionType.PERSONAL, new TextSection("Креативность, инициативность. Пурист кода и архитектуры."));
        listSection_4.put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Реализация протоколов по приему платежей всех основных платежных системы России", "Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django)", "Организация онлайн стажировок и ведение проектов")));
        listSection_4.put(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Python: Django", "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js", "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka")));
        listSection_4.put(SectionType.EXPERIENCE, new OrganizationSection(listExperience_4));
        listSection_4.put(SectionType.EDUCATION, new OrganizationSection(listEducation_4));

        resume_4.setSections(listSection_4);

        // resume_5, For method "update"
        final Resume resume_5 = new Resume("uuid1", "Sidorov Konstantin");

        Map<ContactType, String> listContact_5 = new EnumMap<>(ContactType.class);
        listContact_5.put(ContactType.PHONE, "+7(921) 825-0432");
        listContact_5.put(ContactType.SKYPE, "Konstantin.Sidorov");
        listContact_5.put(ContactType.EMAIL, "KSidorov@yandex.ru");
        listContact_5.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/KSidorov");
        listContact_5.put(ContactType.GITHUB, "https://github.com/KSidorov");
        listContact_5.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/KSidorov");
        listContact_5.put(ContactType.HOME_PAGE, "http://KSidorov.ru/");
        resume_5.setContacts(listContact_5);

        List<Organization> listExperience_5 = new ArrayList<>();
        listExperience_5.add(new Organization(
                "Yota",
                null,
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(1998, OCTOBER), DateUtil.of(2000, JUNE), "Программист", "Создание системы логирования"))));
        listExperience_5.add(new Organization(
                "Wrike",
                "https://www.Wrike.com",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2010, MARCH), DateUtil.of(2018, OCTOBER), "Старший разработчик", "Архитектурные проекты"))));
        listExperience_5.add(new Organization(
                "ООО Плаза",
                null,
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2018, OCTOBER), DateUtil.of(2018, MAY), "Администратор", "Архитектурные проекты"))));

        List<Organization> listEducation_5 = new ArrayList<>();
        listEducation_5.add(new Organization(
                "Coursera",
                "https://www.Coursera.com",
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2000, AUGUST), DateUtil.of(2000, OCTOBER), "Повышение квалификации", null))));
        listEducation_5.add(new Organization(
                "Luxoft",
                null,
                Arrays.asList(
                        new BodyOrganization(DateUtil.of(2010, JUNE), DateUtil.of(2010, OCTOBER), "Курс по оптимизации", null),
                        new BodyOrganization(DateUtil.of(2005, MAY), DateUtil.of(2005, SEPTEMBER), "Курсы повышения квалификации", null))));

        Map<SectionType, AbstractSection> listSection_5 = new EnumMap<>(SectionType.class);
        listSection_5.put(SectionType.OBJECTIVE, new TextSection("Экономист"));
        listSection_5.put(SectionType.PERSONAL, new TextSection("Инициативность."));
        listSection_5.put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Удаленное взаимодействие (JMS/AKKA)", "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk", "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера")));
        listSection_5.put(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS")));
        listSection_5.put(SectionType.EXPERIENCE, new OrganizationSection(listExperience_5));
        listSection_5.put(SectionType.EDUCATION, new OrganizationSection(listEducation_5));

        resume_5.setSections(listSection_5);

        listResume.add(resume_1);
        listResume.add(resume_2);
        listResume.add(resume_3);
        listResume.add(resume_4);
        listResume.add(resume_5);

        return listResume;
    }
}
