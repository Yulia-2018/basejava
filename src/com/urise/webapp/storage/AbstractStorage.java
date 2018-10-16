package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> {
        if (o1.getFullName().compareTo(o2.getFullName()) != 0) {
            return o1.getFullName().compareTo(o2.getFullName());
        } else {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    };

    public void update(Resume resume) {
        final String uuid = resume.getUuid();
        final SK searchKey = getExistedSearchKey(uuid);
        updateObject(searchKey, resume);
    }

    public void save(Resume resume) {
        final String uuid = resume.getUuid();
        final SK searchKey = getNotExistedSearchKey(uuid);
        saveObject(searchKey, resume);
    }

    public Resume get(String uuid) {
        final SK searchKey = getExistedSearchKey(uuid);
        return getObject(searchKey);
    }

    public void delete(String uuid) {
        final SK searchKey = getExistedSearchKey(uuid);
        deleteObject(searchKey);
    }

    private SK getNotExistedSearchKey(String uuid) {
        final SK searchKey = getSearchKey(uuid);
        final boolean exist = isExist(searchKey);
        if (exist) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getExistedSearchKey(String uuid) {
        final SK searchKey = getSearchKey(uuid);
        final boolean exist = isExist(searchKey);
        if (!exist) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    public List<Resume> getAllSorted() {
        final List<Resume> resumeList = getListResume();
        resumeList.sort(RESUME_COMPARATOR);
        return resumeList;
    }

    protected abstract List<Resume> getListResume();

    protected abstract void updateObject(SK searchKey, Resume resume);

    protected abstract void saveObject(SK searchKey, Resume resume);

    protected abstract Resume getObject(SK searchKey);

    protected abstract void deleteObject(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);
}
