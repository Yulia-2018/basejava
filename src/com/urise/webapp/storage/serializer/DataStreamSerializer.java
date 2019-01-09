package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            final Map<ContactType, String> contacts = resume.getContacts();
            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            final Map<SectionType, AbstractSection> sections = resume.getSections();
            writeCollection(dos, sections.entrySet(), entry -> {
                final SectionType type = entry.getKey();
                final AbstractSection section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        final TextSection textSection = (TextSection) section;
                        dos.writeUTF(textSection.getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        final ListSection listSection = (ListSection) section;
                        final List<String> items = listSection.getItems();
                        writeCollection(dos, items, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        final OrganizationSection organizationSection = (OrganizationSection) section;
                        final List<Organization> organizations = organizationSection.getOrganizations();
                        writeCollection(dos, organizations, organization -> {
                            dos.writeUTF(organization.getHomePage().getName());
                            dos.writeUTF(organization.getHomePage().getUrl());
                            final List<Organization.Position> positions = organization.getPositions();
                            writeCollection(dos, positions, position -> {
                                writeLocalDate(dos, position.getStartDate());
                                writeLocalDate(dos, position.getEndDate());
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
            readCollection(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readCollection(dis, () -> {
                final SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.setSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private AbstractSection readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                final List<String> list = new ArrayList<>();
                readCollection(dis, () -> list.add(dis.readUTF()));
                return new ListSection(list);
            case EXPERIENCE:
            case EDUCATION:
                final List<Organization> listOrganization = new ArrayList<>();
                readCollection(dis, () -> {
                    String name = dis.readUTF();
                    String url = dis.readUTF();
                    final List<Organization.Position> listPosition = new ArrayList<>();
                    readCollection(dis, () -> listPosition.add(new Organization.Position(
                            readLocalDate(dis),
                            readLocalDate(dis),
                            dis.readUTF(),
                            dis.readUTF())));
                    listOrganization.add(new Organization(new Link(name, url), listPosition));
                });
                return new OrganizationSection(listOrganization);
            default:
                throw new IllegalStateException();
        }
    }

    private interface ReadElement {
        void read() throws IOException;
    }

    private void readCollection(DataInputStream dis, ReadElement readElement) throws IOException {
        int size = dis.readInt();
        for (int k = 0; k < size; k++) {
            readElement.read();
        }
    }

    private interface WriteElement<T> {
        void write(T element) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, WriteElement<T> writeElement) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writeElement.write(element);
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
        dos.writeInt(1);
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }
}