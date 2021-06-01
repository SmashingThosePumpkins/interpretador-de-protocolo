package br.com.fulltime.app.service;

public class Interpretador {

    public static boolean validarHeader (byte[] array){
        return array[0] == 0x7B;
    }

}
