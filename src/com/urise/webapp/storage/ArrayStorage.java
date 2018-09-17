package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++)
            storage[i] = null;
        size = 0;
    }

    public void update(Resume r) {
        int index = getindex(r.getUuid());
        if (index != -1)
            storage[index] = r;
        else System.out.println("Resume " + r.getUuid() + " не существует");
    }

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Массив с Resume уже заполнен");
        } else {
            if (getindex(r.getUuid()) == -1) {
                storage[size] = r;
                size++;
            } else System.out.println("Resume " + r.getUuid() + " уже существует");
        }
    }

    public Resume get(String uuid) {
        int index = getindex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " не существует");
            return null;
        } else return storage[index];
    }

    public void delete(String uuid) {
        int index = getindex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else System.out.println("Resume " + uuid + " не существует");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] r = new Resume[size];
        for (int i = 0; i < size; i++)
            r[i] = storage[i];
        return r;
    }

    public int size() {
        return size;
    }

    int getindex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) {
                return i;
            }
        }
        return -1;
    }

}
