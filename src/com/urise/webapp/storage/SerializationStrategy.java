package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy {
    public void doWrite(OutputStream os, Resume resume) throws IOException;

    public Resume doRead(InputStream is) throws IOException;
}
