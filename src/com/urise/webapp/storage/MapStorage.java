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
        final String key = resume.getUuid();
        if (!storage.containsKey(key)) {
            notExistObject(key);
        } else {
            storage.replace(key, resume);
        }
    }

    @Override
    public void saveObject(Resume resume) {
        final String key = resume.getUuid();
        if (storage.containsKey(key)) {
            existObject(key);
        } else {
            storage.put(key, resume);
        }
    }

    @Override
    public Resume getObject(String uuid) {
        if (!storage.containsKey(uuid)) {
            notExistObject(uuid);
            return null;
        } else {
            return storage.get(uuid);
        }
    }

    @Override
    public void deleteObject(String uuid) {
        if (!storage.containsKey(uuid)) {
            notExistObject(uuid);
        } else {
            storage.remove(uuid);
        }
    }

    @Override
    public Resume[] getAllObject() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int sizeStorage() {
        return storage.size();
    }
}
