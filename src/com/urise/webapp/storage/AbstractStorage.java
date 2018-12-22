package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    public static final Logger lOG = Logger.getLogger(AbstractStorage.class.getName());

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public void update(Resume resume) {
        lOG.info("Update " + resume);
        final String uuid = resume.getUuid();
        final SK searchKey = getExistedSearchKey(uuid);
        doUpdate(searchKey, resume);
    }

    public void save(Resume resume) {
        lOG.info("Save " + resume);
        final String uuid = resume.getUuid();
        final SK searchKey = getNotExistedSearchKey(uuid);
        doSave(searchKey, resume);
    }

    public Resume get(String uuid) {
        lOG.info("Get " + uuid);
        final SK searchKey = getExistedSearchKey(uuid);
        return doGet(searchKey);
    }

    public void delete(String uuid) {
        lOG.info("Delete " + uuid);
        final SK searchKey = getExistedSearchKey(uuid);
        doDelete(searchKey);
    }

    public List<Resume> getAllSorted() {
        lOG.info("GetAllSorted");
        final List<Resume> resumeList = getListResume();
        resumeList.sort(RESUME_COMPARATOR);
        return resumeList;
    }

    private SK getNotExistedSearchKey(String uuid) {
        final SK searchKey = getSearchKey(uuid);
        final boolean exist = isExist(searchKey);
        if (exist) {
            lOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getExistedSearchKey(String uuid) {
        final SK searchKey = getSearchKey(uuid);
        final boolean exist = isExist(searchKey);
        if (!exist) {
            lOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract List<Resume> getListResume();

    protected abstract void doUpdate(SK searchKey, Resume resume);

    protected abstract void doSave(SK searchKey, Resume resume);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);
}
