package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap();

    @Override
    public void clearStorage() {
        storage.clear();
    }

    @Override
    public void updateObject(Resume resume) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    public void saveObject(Resume resume) {
        final String key = resume.getUuid();
        storage.put(key, resume);
    }

    @Override
    public Resume getObject(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteObject(Object key) {
        storage.remove(key);
    }

    @Override
    public Resume[] getAllObject() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int sizeStorage() {
        return storage.size();
    }

    @Override
    protected String getIndex(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        } else {
            return null;
        }
    }

    @Override
    protected Boolean getExistObject(Object key) {
        if (key == null) {
            return false;
        } else {
            return true;
        }
    }
}
