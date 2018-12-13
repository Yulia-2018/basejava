package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir(); //new File("C:\\Source\\basejava\\storage");

    protected Storage storage;

    private static final List<Resume> LIST_RESUME = ResumeTestData.createResume();
    private static final Resume RESUME_1 = LIST_RESUME.get(0);
    private static final Resume RESUME_2 = LIST_RESUME.get(1);
    private static final Resume RESUME_3 = LIST_RESUME.get(2);
    private static final Resume RESUME_4 = LIST_RESUME.get(3);
    // For method "update"
    private static final Resume RESUME_5 = LIST_RESUME.get(4);

    private static final String UUID_1 = RESUME_1.getUuid();
    private static final String UUID_2 = RESUME_2.getUuid();
    private static final String UUID_3 = RESUME_3.getUuid();
    private static final String UUID_4 = RESUME_4.getUuid();

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(RESUME_5);
        Assert.assertEquals(RESUME_5, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExists() {
        storage.update(RESUME_4);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExists() {
        storage.save(RESUME_1);
    }

    @Test
    public void get() {
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
        Assert.assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExists() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExists() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> result = storage.getAllSorted();
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(Arrays.asList(RESUME_2, RESUME_1, RESUME_3), result);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}