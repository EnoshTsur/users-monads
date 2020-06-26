package com.enosh.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
public class UsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

}

class Main {

    public static void main(String[] args) {
        Monad<String> myMonad = Monad.ofNullable(null);

        System.out.println(
                myMonad.orElseGet(() -> "Gogog")
        );
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
        return supplier.get();
    }


    //    public <R> R map(Function<T, Monad<R>> mapper) {
//        return mapper.apply(content);
//    }
}
