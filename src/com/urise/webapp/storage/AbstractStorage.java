package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void clear() {
        clearStorage();
    }

    public void update(Resume resume) {
        final String uuid = resume.getUuid();
        final Boolean check = checkExistObject(uuid);
        if (check) {
            updateObject(resume);
        } else {
            notExistObject(uuid);
        }
    }

    public void save(Resume resume) {
        final String uuid = resume.getUuid();
        final Boolean check = checkExistObject(uuid);
        if (!check) {
            saveObject(resume);
        } else {
            existObject(uuid);
        }
    }

    public Resume get(String uuid) {
        final Boolean check = checkExistObject(uuid);
        if (check) {
            return getObject(uuid);
        } else {
            notExistObject(uuid);
        }
        return null;
    }

    public void delete(String uuid) {
        final Object key = getIndex(uuid);
        final Boolean check = getExistObject(key);
        if (check) {
            deleteObject(key);
        } else {
            notExistObject(uuid);
        }
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

    protected abstract void deleteObject(Object key);

    protected abstract Resume[] getAllObject();

    protected abstract int sizeStorage();

    protected abstract Object getIndex(String uuid);

    protected abstract Boolean getExistObject(Object key);

    protected Boolean checkExistObject(String uuid) {
        final Object key = getIndex(uuid);
        return getExistObject(key);
    }
}
