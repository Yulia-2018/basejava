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
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            final Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        final TextSection textSection = (TextSection) resume.getSection(entry.getKey());
                        dos.writeUTF(textSection.getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        final ListSection listSection = (ListSection) resume.getSection(entry.getKey());
                        final List<String> items = listSection.getItems();
                        dos.writeInt(items.size());
                        for (String item : items) {
                            dos.writeUTF(item);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        final OrganizationSection organizationSection = (OrganizationSection) resume.getSection(entry.getKey());
                        final List<Organization> organizations = organizationSection.getOrganizations();
                        dos.writeInt(organizations.size());
                        for (Organization organization : organizations) {
                            dos.writeUTF(organization.getHomePage().getName());
                            writeNotRequiredFields(dos, organization.getHomePage().getUrl());
                            final List<Organization.Position> positions = organization.getPositions();
                            dos.writeInt(positions.size());
                            for (Organization.Position position : positions) {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                writeNotRequiredFields(dos, position.getDescription());
                            }
                        }
                        break;
                }
            }
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

    private void writeNotRequiredFields(DataOutputStream dos, String value) throws IOException {
        if (value == null) {
            dos.writeUTF(NULL_HOLDER);
        } else {
            dos.writeUTF(value);
        }
    }

    private String readNotRequiredFields(DataInputStream dis) throws IOException {
        String value = dis.readUTF();
        if (NULL_HOLDER.equals(value)) {
            value = null;
        }
        return value;
    }
}