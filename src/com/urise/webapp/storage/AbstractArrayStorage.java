package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

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
    protected void updateObject(Object index, Resume resume) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected void saveObject(Object index, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            saveResume((Integer) index, resume);
            size++;
        }
    }

    @Override
    protected Resume getObject(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected void deleteObject(Object index) {
        deleteResume((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return sorted list, contains only Resumes in storage (without null)
     */
    @Override
    protected List<Resume> getListResume() {
        final Resume[] resumes = Arrays.copyOfRange(storage, 0, size);
        return Arrays.asList(resumes);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract void saveResume(int index, Resume resume);

    protected abstract void deleteResume(int index);
}
