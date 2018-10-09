package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        final String uuid = resume.getUuid();
        final Object searchKey = getSearchKey(uuid);
        final Boolean check = getExistObject(searchKey);
        if (check) {
            updateObject(searchKey, resume);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void save(Resume resume) {
        final String uuid = resume.getUuid();
        final Object searchKey = getSearchKey(uuid);
        final Boolean check = getExistObject(searchKey);
        if (!check) {
            saveObject(searchKey, resume);
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    public Resume get(String uuid) {
        final Object searchKey = getSearchKey(uuid);
        final Boolean check = getExistObject(searchKey);
        if (check) {
            return getObject(searchKey);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void delete(String uuid) {
        final Object searchKey = getSearchKey(uuid);
        final Boolean check = getExistObject(searchKey);
        if (check) {
            deleteObject(searchKey);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void updateObject(Object searchKey, Resume resume);

    protected abstract void saveObject(Object searchKey, Resume resume);

    protected abstract Resume getObject(Object searchKey);

    protected abstract void deleteObject(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract Boolean getExistObject(Object searchKey);
}
