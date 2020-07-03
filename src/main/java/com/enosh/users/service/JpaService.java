package com.enosh.users.service;

import com.enosh.users.exceptions.NotExistException;
import com.enosh.users.model.BaseEntity;
import com.enosh.users.model.User;
import com.enosh.users.utils.LogUtils;
import com.enosh.users.utils.MessageUtils;
import io.vavr.Function1;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.enosh.users.utils.MessageUtils.doesNotExists;

// T ( User )
// ID ( Long )
public interface JpaService<T extends BaseEntity, ID> {

    Optional<T> findById(ID id);

    Either<Throwable, T> save(T t);

    Either<Throwable, T> update(Function1<T, T> mapper, ID id);

    Either<Throwable, T> deleteById(ID id);

    List<T> findAll();

    default <T, ID> Supplier<Either<Throwable, T>> logAndEitherLeft(ID id, String operation) {
        return () -> {
            NotExistException exception = doesNotExists(BaseEntity.class)
                    .andThen(NotExistException::new)
                    .apply(id, operation);
            LogUtils.printError(exception);
            return Either.left(exception);
        };
    }

    default Function<T, Either<Throwable, T>> tryToUpdate(JpaRepository<T, ID> repository, Function<T, T> mapper) {
        return entity -> {
            Try<T> tryAction = Try.of(() -> mapper.andThen(repository::save).apply(entity));
            tryAction.onSuccess(LogUtils::printSuccess);
            tryAction.onFailure(LogUtils::printError);
            return tryAction.toEither();
        };
    }

    default Function<T, Either<Throwable, T>> tryToDelete(JpaRepository<T, ID> repository) {
        return entity -> {
            Try<T> tryAction = Try.of(() -> {
                repository.delete(entity);
                return entity;
            });
            tryAction.onSuccess(LogUtils::printSuccess);
            tryAction.onFailure(LogUtils::printError);
            return tryAction.toEither();
        };
    }



}
