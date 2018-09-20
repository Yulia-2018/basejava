package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index <= -1) {
            if (size >= STORAGE_LIMIT) {
                System.out.println("Storage overflow");
            } else {
                int newIndex = -index - 1;
                for (int i = size; i > newIndex; i--) {
                    storage[i] = storage[i - 1];
                }
                storage[newIndex] = resume;
                size++;
            }
        } else {
            System.out.println("Resume " + resume.getUuid() + " already exist");
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            for (int i = index; i < size - 1; i++) {
                storage[i] = storage[i + 1];
            }
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume " + uuid + " not exist");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

}
