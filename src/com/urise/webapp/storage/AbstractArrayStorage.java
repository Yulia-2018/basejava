package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void updateObject(Object searchKey, Resume resume) {
        storage[(Integer) searchKey] = resume;
    }

    @Override
    public void saveObject(Object searchKey, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            saveResume((Integer) searchKey, resume);
            size++;
        }
    }

    @Override
    public Resume getObject(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    public void deleteObject(Object searchKey) {
        deleteResume((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected Boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    protected abstract void saveResume(int index, Resume resume);

    protected abstract void deleteResume(int index);
}
