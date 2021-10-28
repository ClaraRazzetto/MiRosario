package com.mirosario.MiRosario.excepciones;

public class ErrorServicio extends Exception {

    public ErrorServicio() {
    }

    public ErrorServicio(String msg) {
        super(msg);
    }
}
