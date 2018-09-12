/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        for (int i = 0; i < size; i++)
            storage[i] = null;
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size = size + 1;
    }

    Resume get(String uuid) {
        Resume r = null;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                r = storage[i];
                break;
            }
        }
        return r;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                for (int j = i; j < size - 1; j++)
                    storage[j] = storage[j + 1];
                size = size - 1;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] r = new Resume[size];
        for (int i = 0; i < size; i++)
            r[i] = storage[i];
        return r;
    }

    int size() {
        return size;
    }
}
