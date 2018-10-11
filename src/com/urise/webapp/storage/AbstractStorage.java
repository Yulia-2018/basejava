package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        final String uuid = resume.getUuid();
        final Object searchKey = getExistedSearchKey(uuid);
        updateObject(searchKey, resume);
    }

    public void save(Resume resume) {
        final String uuid = resume.getUuid();
        final Object searchKey = getNotExistedSearchKey(uuid);
        saveObject(searchKey, resume);
    }

    public Resume get(String uuid) {
        final Object searchKey = getExistedSearchKey(uuid);
        return getObject(searchKey);
    }

    public void delete(String uuid) {
        final Object searchKey = getExistedSearchKey(uuid);
        deleteObject(searchKey);
    }

    private Object getNotExistedSearchKey(String uuid) {
        final Object searchKey = getSearchKey(uuid);
        final boolean exist = isExist(searchKey);
        if (exist) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getExistedSearchKey(String uuid) {
        final Object searchKey = getSearchKey(uuid);
        final boolean exist = isExist(searchKey);
        if (!exist) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void updateObject(Object searchKey, Resume resume);

    protected abstract void saveObject(Object searchKey, Resume resume);

    protected abstract Resume getObject(Object searchKey);

    protected abstract void deleteObject(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);
}
