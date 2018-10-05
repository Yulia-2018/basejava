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
        final int index = storage.indexOf(resume);
        if (index <= -1) {
            notExistObject(resume.getUuid());
        } else {
            storage.set(index, resume);
        }
    }

    @Override
    public void saveObject(Resume resume) {
        if (storage.contains(resume)) {
            existObject(resume.getUuid());
        } else {
            storage.add(resume);
        }
    }

    @Override
    public Resume getObject(String uuid) {
        final int index = storage.indexOf(new Resume(uuid));
        if (index <= -1) {
            notExistObject(uuid);
            return null;
        } else {
            return storage.get(index);
        }
    }

    @Override
    public void deleteObject(String uuid) {
        final Resume resume = new Resume(uuid);
        if (!storage.contains(resume)) {
            notExistObject(uuid);
        } else {
            storage.remove(resume);
        }
    }

    @Override
    public Resume[] getAllObject() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int sizeStorage() {
        return storage.size();
    }
}
