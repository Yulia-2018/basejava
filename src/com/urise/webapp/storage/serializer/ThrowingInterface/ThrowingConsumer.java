package com.urise.webapp.storage.serializer.ThrowingInterface;

import java.util.function.Consumer;

public interface ThrowingConsumer<T> extends Consumer<T>{
    @Override
    default void accept(T t) {
        try {
            acceptThrows(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void acceptThrows(T t) throws Exception;
}
