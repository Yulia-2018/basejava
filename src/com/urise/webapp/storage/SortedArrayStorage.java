package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(int index, Resume resume) {
        int newIndex = -index - 1;
        if (newIndex != size) {
            System.arraycopy(storage, newIndex, storage, newIndex + 1, size - newIndex);
        }
        storage[newIndex] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        if (index < size - 1) {
            int length = size - index - 1;
            System.arraycopy(storage, index + 1, storage, index, length);
        }
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected Boolean getExistObject(Object searchKey) {
        if ((Integer) searchKey <= -1) {
            return false;
        } else {
            return true;
        }
    }

}
