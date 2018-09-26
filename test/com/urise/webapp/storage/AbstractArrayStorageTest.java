package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    public static final String UUID_1 = "uuid1";
    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
        storage.get(UUID_1);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        Assert.assertEquals(3, storage.size());
        Assert.assertSame(newResume, storage.get(UUID_1));
    }

    @Test
    public void save() {
        Resume newResume = new Resume("uuid4");
        storage.save(newResume);
        Assert.assertEquals(4, storage.size());
        Assert.assertSame(newResume, storage.get("uuid4"));
    }

    @Test
    public void get() {
        Assert.assertSame(new Resume(UUID_1), storage.get(UUID_1));
        Assert.assertSame(new Resume(UUID_2), storage.get(UUID_2));
        Assert.assertSame(new Resume(UUID_3), storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExists() {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void getExists() {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void overflow() {
        try {
            for (int i = 3; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i + 1));
            }
        } catch (StorageException ex) {
            Assert.fail();
        }
        storage.save(new Resume("uuid" + STORAGE_LIMIT + 1));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test
    public void getAll() {
        storage.getAll();
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}