package com.behzadnazarbakhsh.finances.util;

public class NotImplementedException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public NotImplementedException(){
        super("Unimplemented code was executed!");
    }
}
