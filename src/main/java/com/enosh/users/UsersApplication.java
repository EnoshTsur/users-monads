package com.enosh.users;

import org.hibernate.boot.model.source.spi.EmbeddableMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.enosh.users.MyFunctions.*;

@SpringBootApplication
public class UsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

}

interface MyFunctions {

    Predicate<String> isNotStartWithA = str -> !str.startsWith("a");
    Function<String, String> ifContainsSpaceThanHi = str -> str.contains(" ") ? str + " hi" : str;
}

class Main {

    public static void main(String[] args) {



        Stream.of("avi", "ron", "koby ")
                .filter(isNotStartWithA)
                .map(ifContainsSpaceThanHi)
                .forEach(System.out::println);

    }
}

class Monad<T> {

    private T content;

    private Monad(T content) {
        this.content = content;
    }

    static <T> Monad<T> of(T content) {
        return new Monad<>(Objects.requireNonNull(content));
    }

    static <T> Monad<T> ofNullable(T content) {
        return new Monad<>(content);
    }

    public T get() {
        return Objects.requireNonNull(content);
    }

    public T orElse(T other) {
        return content == null ? other : content;
    }

    public T orElseGet(Supplier<T> supplier) {
        return content == null ? supplier.get() : content;
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exception) throws X {
        if (content == null) {
            throw  exception.get();
        }
        return content;
    }

    public <R> Monad<R> map(Function<T, R> mapper) {
        return content == null ? Monad.ofNullable(null) :
                Monad.ofNullable(mapper.apply(content));
    }
}
