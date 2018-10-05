package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void clear() {
        clearStorage();
    }

    public void update(Resume resume) {
        updateObject(resume);
    }

    public void save(Resume resume) {
        saveObject(resume);
    }

    public Resume get(String uuid) {
        return getObject(uuid);
    }

    public void delete(String uuid) {
        deleteObject(uuid);
    }

    public Resume[] getAll() {
        return getAllObject();
    }

    public int size() {
        return sizeStorage();
    }

    protected void existObject(String uuid) {
        throw new ExistStorageException(uuid);
    }

    protected void notExistObject(String uuid) {
        throw new NotExistStorageException(uuid);
    }

    protected abstract void clearStorage();

    protected abstract void updateObject(Resume resume);

    protected abstract void saveObject(Resume resume);

    protected abstract Resume getObject(String uuid);

    protected abstract void deleteObject(String uuid);

    protected abstract Resume[] getAllObject();

    protected abstract int sizeStorage();
}
