package com.urise.webapp.storage.serializer.ThrowingInterface;

import java.util.function.IntConsumer;

public interface ThrowingIntConsumer extends IntConsumer {
    @Override
    default void accept(int value) {
        try {
            acceptThrows(value);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    void acceptThrows(int value) throws Exception;
}
