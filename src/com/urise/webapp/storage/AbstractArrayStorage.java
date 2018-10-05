package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clearStorage() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void updateObject(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            notExistObject(resume.getUuid());
        }
    }

    public void saveObject(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index <= -1) {
            if (size >= STORAGE_LIMIT) {
                throw new StorageException("Storage overflow", resume.getUuid());
            } else {
                saveResume(resume, index);
                size++;
            }
        } else {
            existObject(resume.getUuid());
        }
    }

    public Resume getObject(String uuid) {
        int index = getIndex(uuid);
        if (index <= -1) {
            notExistObject(uuid);
            return null;
        } else {
            return storage[index];
        }
    }

    public void deleteObject(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            storage[size - 1] = null;
            size--;
        } else {
            notExistObject(uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAllObject() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int sizeStorage() {
        return size;
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void deleteResume(int index);

    protected abstract int getIndex(String uuid);

}
