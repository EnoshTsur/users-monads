package com.enosh.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

// Data Transfer Object

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Dto<T>{

    private boolean success;
    private LocalDateTime date;
    private T content;

}
