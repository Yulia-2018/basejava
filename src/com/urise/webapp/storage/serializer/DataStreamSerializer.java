package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.serializer.ThrowingInterface.ThrowingBiConsumer;
import com.urise.webapp.storage.serializer.ThrowingInterface.ThrowingConsumer;
import com.urise.webapp.storage.serializer.ThrowingInterface.ThrowingIntConsumer;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            final Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            contacts.forEach((ThrowingBiConsumer<ContactType, String>) (type, s) -> {
                dos.writeUTF(type.name());
                dos.writeUTF(s);
            });

            final Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            sections.forEach((ThrowingBiConsumer<SectionType, AbstractSection>) (type, abstractSection) -> {
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
                        items.forEach((ThrowingConsumer<String>) dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        final OrganizationSection organizationSection = (OrganizationSection) resume.getSection(type);
                        final List<Organization> organizations = organizationSection.getOrganizations();
                        dos.writeInt(organizations.size());
                        organizations.forEach((ThrowingConsumer<Organization>) organization -> {
                            dos.writeUTF(organization.getHomePage().getName());
                            dos.writeUTF(organization.getHomePage().getUrl());
                            final List<Organization.Position> positions = organization.getPositions();
                            dos.writeInt(positions.size());
                            positions.forEach((ThrowingConsumer<Organization.Position>) position -> {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription());
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
            IntStream.range(0, sizeContacts).forEach((ThrowingIntConsumer) value -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            int sizeSections = dis.readInt();
            IntStream.range(0, sizeSections).forEach((ThrowingIntConsumer) value -> {
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
                        IntStream.range(0, sizeList).forEach((ThrowingIntConsumer) value1 -> list.add(dis.readUTF()));
                        resume.addSection(sectionType, new ListSection(list));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int sizeOrganization = dis.readInt();
                        final List<Organization> listOrganization = new ArrayList<>();
                        IntStream.range(0, sizeOrganization).forEach((ThrowingIntConsumer) value1 -> {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            final List<Organization.Position> listPosition = new ArrayList<>();
                            int sizePosition = dis.readInt();
                            IntStream.range(0, sizePosition).forEach((ThrowingIntConsumer) value2 -> listPosition.add(new Organization.Position(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF())));
                            listOrganization.add(new Organization(new Link(name, url), listPosition));
                        });
                        resume.addSection(sectionType, new OrganizationSection(listOrganization));
                        break;
                }
            });
            return resume;
        }
    }
}