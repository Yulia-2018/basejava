package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.DataStreamSerializer;

public class DataFileStorageTest extends AbstractStorageTest{
    public DataFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new DataStreamSerializer()));
    }
}
