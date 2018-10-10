package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void updateObject(Object searchKey, Resume resume) {
        storage.replace((String) searchKey, resume);
    }

    @Override
    public void saveObject(Object searchKey, Resume resume) {
        storage.put((String) searchKey, resume);
    }

    @Override
    public Resume getObject(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void deleteObject(Object searchKey) {
        storage.remove(searchKey);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
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
    protected Boolean isExist(Object searchKey) {
        return storage.containsKey(searchKey);
    }
}
