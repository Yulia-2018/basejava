package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clearStorage() {
        storage.clear();
    }

    @Override
    public void updateObject(Resume resume) {
        storage.set(getIndex(resume.getUuid()), resume);
    }

    @Override
    public void saveObject(Resume resume) {
        storage.add(resume);
    }

    @Override
    public Resume getObject(String uuid) {
        final int index = getIndex(uuid);
        return storage.get(index);
    }

    @Override
    protected void deleteObject(Object key) {
        final int index = (Integer) key;
        storage.remove(index);
    }

    @Override
    public Resume[] getAllObject() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int sizeStorage() {
        return storage.size();
    }

    @Override
    protected Integer getIndex(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected Boolean getExistObject(Object key) {
        if ((Integer) key <= -1) {
            return false;
        } else {
            return true;
        }
    }
}
