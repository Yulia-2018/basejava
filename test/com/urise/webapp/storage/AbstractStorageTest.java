package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {

    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1 = new Resume(UUID_1, "Petrov Maxim");
    private static final Resume RESUME_2 = new Resume(UUID_2, "Inanov Dmitrii");
    private static final Resume RESUME_3 = new Resume(UUID_3, "Petrov Maxim");
    private static final Resume RESUME_4 = new Resume(UUID_4, "Inanov Petr");
    // For method "update"
    private static final Resume RESUME_5 = new Resume(UUID_1, "Sidorov Konstantin");

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
        Assert.assertSame(RESUME_5, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExists() {
        storage.update(RESUME_4);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertSame(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExists() {
        storage.save(RESUME_1);
    }

    @Test
    public void get() {
        Assert.assertSame(RESUME_1, storage.get(UUID_1));
        Assert.assertSame(RESUME_2, storage.get(UUID_2));
        Assert.assertSame(RESUME_3, storage.get(UUID_3));
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
        Assert.assertEquals(result.size(), storage.size());
        Assert.assertEquals(result, Arrays.asList(RESUME_2, RESUME_1, RESUME_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}