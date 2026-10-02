package com.behzadnazarbakhsh.finances.util;

public class UnreachableCodeException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public UnreachableCodeException(){
        super("Unreachable code was executed");
    }
}
