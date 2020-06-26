package com.enosh.users.service;

import com.enosh.users.model.User;
import io.vavr.Function1;
import io.vavr.control.Either;

import java.util.List;
import java.util.Optional;

public interface JpaService<T, ID> {

    Optional<T> findById(ID id);

    Either<Throwable, T> save(T t);

    Either<Throwable, T> update(Function1<T, T> mapper, ID id);

    Either<Throwable, T> deleteById(ID id);

    List<T> findAll();




}
