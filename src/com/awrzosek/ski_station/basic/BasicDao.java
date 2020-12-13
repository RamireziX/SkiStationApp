package com.awrzosek.ski_station.basic;

import java.util.List;
import java.util.Optional;

public interface BasicDao<T> {
    Optional<T> get(Long id);
    List<T> getAll();
    void add(T t);
    void update(T t);
    void delete(T t);
}
