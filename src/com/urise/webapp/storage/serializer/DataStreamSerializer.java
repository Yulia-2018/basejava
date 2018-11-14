package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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
                try {
                    dos.writeUTF(type.name());
                    dos.writeUTF(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            final Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            sections.forEach((type, abstractSection) -> {
                try {
                    dos.writeUTF(type.name());
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            final TextSection textSection = (TextSection) resume.getSection(type);
                            dos.writeUTF(textSection.getContent());
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            final ListSection listSection = (ListSection) resume.getSection(type);
                            final List<String> items = listSection.getItems();
                            dos.writeInt(items.size());
                            items.forEach(s -> {
                                try {
                                    dos.writeUTF(s);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            final OrganizationSection organizationSection = (OrganizationSection) resume.getSection(type);
                            final List<Organization> organizations = organizationSection.getOrganizations();
                            dos.writeInt(organizations.size());
                            organizations.forEach(organization -> {
                                try {
                                    dos.writeUTF(organization.getHomePage().getName());
                                    writeNotRequiredFields(dos, organization.getHomePage().getUrl());
                                    final List<Organization.Position> positions = organization.getPositions();
                                    dos.writeInt(positions.size());
                                    positions.forEach(position -> {
                                        try {
                                            dos.writeUTF(position.getStartDate().toString());
                                            dos.writeUTF(position.getEndDate().toString());
                                            dos.writeUTF(position.getTitle());
                                            writeNotRequiredFields(dos, position.getDescription());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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
            IntStream.range(0, sizeContacts).forEach(val -> {
                try {
                    resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            int sizeSections = dis.readInt();
            IntStream.range(0, sizeSections).forEach(val -> {
                try {
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
                            IntStream.range(0, sizeList).forEach(val1 -> {
                                try {
                                    list.add(dis.readUTF());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            resume.addSection(sectionType, new ListSection(list));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            int sizeOrganization = dis.readInt();
                            final List<Organization> listOrganization = new ArrayList<>();
                            IntStream.range(0, sizeOrganization).forEach(val1 -> {
                                try {
                                    String name = dis.readUTF();
                                    String url = readNotRequiredFields(dis);
                                    final List<Organization.Position> listPosition = new ArrayList<>();
                                    int sizePosition = dis.readInt();
                                    IntStream.range(0, sizePosition).forEach(val2 -> {
                                        try {
                                            listPosition.add(new Organization.Position(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), readNotRequiredFields(dis)));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    });
                                    listOrganization.add(new Organization(new Link(name, url), listPosition));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            resume.addSection(sectionType, new OrganizationSection(listOrganization));
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
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