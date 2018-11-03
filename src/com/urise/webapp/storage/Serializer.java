package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Serializer {
    private SerializationStrategy serializationStrategy;

    public void setSerializationStrategy(SerializationStrategy serializationStrategy) {
        this.serializationStrategy = serializationStrategy;
    }

    public void executeDoWrite(OutputStream os, Resume resume) throws IOException {
        serializationStrategy.doWrite(os, resume);
    }

    public Resume executeDoRead(InputStream is) throws IOException {
        return serializationStrategy.doRead(is);
    }
}
