package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    private static final String NULL_HOLDER = "Null_Holder";

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            final Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            contacts.forEach((type, s) -> {
                write(dos, type.name());
                write(dos, s);
            });

            final Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            sections.forEach((type, abstractSection) -> {
                write(dos, type.name());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        final TextSection textSection = (TextSection) resume.getSection(type);
                        write(dos, textSection.getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        final ListSection listSection = (ListSection) resume.getSection(type);
                        final List<String> items = listSection.getItems();
                        write(dos, items.size());
                        items.forEach(s -> write(dos, s));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        final OrganizationSection organizationSection = (OrganizationSection) resume.getSection(type);
                        final List<Organization> organizations = organizationSection.getOrganizations();
                        write(dos, organizations.size());
                        organizations.forEach(organization -> {
                            write(dos, organization.getHomePage().getName());
                            writeNotRequiredFields(dos, organization.getHomePage().getUrl());
                            final List<Organization.Position> positions = organization.getPositions();
                            write(dos, positions.size());
                            positions.forEach(position -> {
                                write(dos, position.getStartDate().toString());
                                write(dos, position.getEndDate().toString());
                                write(dos, position.getTitle());
                                writeNotRequiredFields(dos, position.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int sizeContacts = dis.readInt();
            for (int i = 0; i < sizeContacts; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sizeSections = dis.readInt();
            for (int i = 0; i < sizeSections; i++) {
                final SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int sizeList = dis.readInt();
                        final List<String> list = new ArrayList<>();
                        for (int j = 0; j < sizeList; j++) {
                            list.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(list));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int sizeOrganization = dis.readInt();
                        final List<Organization> listOrganization = new ArrayList<>();
                        for (int j = 0; j < sizeOrganization; j++) {
                            String name = dis.readUTF();
                            String url = readNotRequiredFields(dis);
                            final List<Organization.Position> listPosition = new ArrayList<>();
                            int sizePosition = dis.readInt();
                            for (int k = 0; k < sizePosition; k++) {
                                listPosition.add(new Organization.Position(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), readNotRequiredFields(dis)));
                            }
                            listOrganization.add(new Organization(new Link(name, url), listPosition));
                        }
                        resume.addSection(sectionType, new OrganizationSection(listOrganization));
                        break;
                }
            }
            return resume;
        }
    }

    private void writeNotRequiredFields(DataOutputStream dos, String value) {
        if (value == null) {
            write(dos, NULL_HOLDER);
        } else {
            write(dos, value);
        }
    }

    private String readNotRequiredFields(DataInputStream dis) throws IOException {
        String value = dis.readUTF();
        if (NULL_HOLDER.equals(value)) {
            value = null;
        }
        return value;
    }

    private void write(DataOutputStream dos, Object value) {
        try {
            if (value instanceof String) {
                dos.writeUTF((String) value);
            } else if (value instanceof Integer) {
                dos.writeInt((int) value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}