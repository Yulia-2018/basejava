package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private Serializer serializer = new Serializer();

    protected PathStorage(String dir, SerializationStrategy serializationStrategy) {
        this.directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        serializer.setSerializationStrategy(serializationStrategy);
    }

    @Override
    protected List<Resume> getListResume() {
        List<Resume> listResume = new ArrayList<>();
        try {
            Files.list(directory).forEach(path -> listResume.add(doGet(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", null, e);
        }
        return listResume;
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            serializer.executeDoWrite(Files.newOutputStream(path), resume);
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn`t create path " + path, resume.getUuid(), e);
        }
        doUpdate(path, resume);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializer.executeDoRead(Files.newInputStream(path));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        try {
            return Files.list(directory).toArray().length;
        } catch (IOException e) {
            throw new StorageException("Path sizing error", null, e);
        }
    }
}
