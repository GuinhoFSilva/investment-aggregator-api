package com.guinhofsilva.agregadorinvestimentos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyRequestBodyException extends RuntimeException{
    public EmptyRequestBodyException(){
        super("The Request Body Cannot Be Null!");
    }
}
