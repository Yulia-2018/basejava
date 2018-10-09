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
    public void updateObject(Object searchKey, Resume resume) {
        storage.set((Integer) searchKey, resume);
    }

    @Override
    public void saveObject(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    public Resume getObject(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    protected void deleteObject(Object searchKey) {
        final int index = (Integer) searchKey;
        storage.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected Boolean getExistObject(Object searchKey) {
        if ((Integer) searchKey <= -1) {
            return false;
        } else {
            return true;
        }
    }
}
