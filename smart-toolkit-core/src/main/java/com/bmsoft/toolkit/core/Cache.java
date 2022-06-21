package com.bmsoft.toolkit.core;

/**
 * @author llk
 * @date 2020-11-02 17:47
 */
public interface Cache<T> {


    default T get(String key) {
        throw new UnsupportedOperationException("not allow.");
    }

    default void set(String key, T value) {
        throw new UnsupportedOperationException("not allow.");
    }

    default void delete(String key) {
        throw new UnsupportedOperationException("not allow.");
    }

    default void add(T value) {
        throw new UnsupportedOperationException("not allow.");
    }

    default void removeKey(String key) {
        throw new UnsupportedOperationException("not allow.");
    }

    default void removeVal(T value) {
        throw new UnsupportedOperationException("not allow.");
    }

    default void put(String key, T value) {
        throw new UnsupportedOperationException("not allow.");
    }

    default boolean contains(String key) {
        throw new UnsupportedOperationException("not allow.");
    }

    default boolean has(T value) {
        throw new UnsupportedOperationException("not allow.");
    }

    default void clear() {
        throw new UnsupportedOperationException("not allow.");
    }


}
