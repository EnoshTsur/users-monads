package com.enosh.users.service;

import com.enosh.users.exceptions.NotExistException;
import com.enosh.users.model.User;
import com.enosh.users.repository.UserRepository;
import com.enosh.users.utils.LogUtils;
import com.enosh.users.utils.MessageUtils;
import io.vavr.Function1;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.enosh.users.utils.LogUtils.*;
import static com.enosh.users.utils.MessageUtils.*;

@AllArgsConstructor
@Slf4j
@Service
public class UserService implements JpaService<User, Long> {

    private final UserRepository repository;

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Either<Throwable, User> save(User user) {
        Try<User> saveUser = Try.of(() -> repository.save(user));
        saveUser.onSuccess(LogUtils::printSuccess);
        saveUser.onFailure(LogUtils::printError);
        return saveUser.toEither();
    }


    @Override
    public Either<Throwable, User> update(Function1<User, User> mapper, Long id) {
        return findById(id)
                .map(user -> {
                    Try<User> tryToUpdate = Try.of(() -> mapper.andThen(repository::save).apply(user));
                    tryToUpdate.onSuccess(LogUtils::printSuccess);
                    tryToUpdate.onFailure(LogUtils::printError);
                    return tryToUpdate.toEither();
                })
                .orElseGet(() ->  {
                    NotExistException exception = userDoesNotExists
                            .andThen(NotExistException::new)
                            .apply(id, "update");

                    LogUtils.printError(exception);
                    return Either.left(exception);
                });
    }

    @Override
    public Either<Throwable, User> deleteById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}



