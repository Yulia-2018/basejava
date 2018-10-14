package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateObject(Object searchKey, Resume resume) {
        storage.replace((String) searchKey, resume);
    }

    @Override
    protected void saveObject(Object searchKey, Resume resume) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected Resume getObject(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void deleteObject(Object searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected List<Resume> getListResume() {
        final Resume[] resumes = storage.values().toArray(new Resume[storage.size()]);
        return Arrays.asList(resumes);
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
    protected boolean isExist(Object searchKey) {
        return storage.containsKey(searchKey);
    }
}
