package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("Resume " + resume.getUuid() + " not exist");
        }
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index <= -1) {
            if (size >= STORAGE_LIMIT) {
                System.out.println("Storage overflow");
            } else {
                saveResume(resume, index);
                size++;
            }
        } else {
            System.out.println("Resume " + resume.getUuid() + " already exist");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index <= -1) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume " + uuid + " not exist");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void deleteResume(int index);

    protected abstract int getIndex(String uuid);

}
