package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateObject(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void saveObject(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getObject(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void deleteObject(Integer index) {
        storage.remove(index.intValue());
    }

    @Override
    protected List<Resume> getListResume() {
        return storage.subList(0, storage.size());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }
}
