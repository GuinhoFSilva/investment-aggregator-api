package com.guinhofsilva.agregadorinvestimentos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameAlreadyUsedException extends RuntimeException{
    public UsernameAlreadyUsedException(String message){
        super(message);
    }
}
