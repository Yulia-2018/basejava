package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clearStorage() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void updateObject(Resume resume) {
        final Integer index = (Integer) getIndex(resume.getUuid());
        storage[index] = resume;
    }

    @Override
    public void saveObject(Resume resume) {
        final Integer index = (Integer) getIndex(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            saveResume(resume, index);
            size++;
        }
    }

    @Override
    public Resume getObject(String uuid) {
        final Integer index = (Integer) getIndex(uuid);
        return storage[index];
    }

    @Override
    public void deleteObject(Object key) {
        deleteResume((Integer) key);
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAllObject() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int sizeStorage() {
        return size;
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void deleteResume(int index);
}
