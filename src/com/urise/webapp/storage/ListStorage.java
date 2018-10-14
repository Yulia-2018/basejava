package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateObject(Object searchKey, Resume resume) {
        storage.set((Integer) searchKey, resume);
    }

    @Override
    protected void saveObject(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getObject(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    protected void deleteObject(Object searchKey) {
        final int index = (Integer) searchKey;
        storage.remove(index);
    }

    @Override
    protected List<Resume> getListResume() {
        return storage;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }
}
