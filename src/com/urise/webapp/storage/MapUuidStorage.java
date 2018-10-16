package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateObject(String uuid, Resume resume) {
        storage.replace(uuid, resume);
    }

    @Override
    protected void saveObject(String uuid, Resume resume) {
        storage.put(uuid, resume);
    }

    @Override
    protected Resume getObject(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteObject(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected List<Resume> getListResume() {
        return new ArrayList(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String uuid) {
        return storage.containsKey(uuid);
    }
}
