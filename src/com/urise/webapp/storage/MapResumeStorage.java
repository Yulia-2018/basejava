package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateObject(Object searchKey, Resume resume) {
        final Resume keyResume = (Resume) searchKey;
        storage.replace(keyResume.getUuid(), resume);
    }

    @Override
    protected void saveObject(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getObject(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void deleteObject(Object searchKey) {
        final Resume keyResume = (Resume) searchKey;
        storage.remove(keyResume.getUuid());
    }

    @Override
    protected List<Resume> getListResume() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsValue(searchKey);
    }
}
